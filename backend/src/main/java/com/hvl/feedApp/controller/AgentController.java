package com.hvl.feedApp.controller;

import com.hvl.feedApp.security.Authenticator;
import com.hvl.feedApp.Agent;
import com.hvl.feedApp.Poll;
import com.hvl.feedApp.security.Authorizer;
import com.hvl.feedApp.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/agents")
public class AgentController {
    private final AgentService agentService;
    private final Authenticator authenticator;
    private final Authorizer authorizer;
    @Autowired
    public AgentController(AgentService agentService, Authorizer authorizer){
        this.agentService = agentService;
        this.authenticator = new Authenticator(agentService);
        this.authorizer = new Authorizer();
    }

    @GetMapping()
    public List<Agent> getAgents(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/agents", "GET")) {
                return agentService.getAgents();
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @GetMapping(path = "{id}")
    public Agent getById(@PathVariable("id") Long agentID, @RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth){
        if(authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/agents/{id}", "GET")) {
                return agentService.getById(agentID);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @GetMapping("test")
    public List<Poll> getOwnedPolls(@RequestParam String ownedPolls, @RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth) {
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/agents/test", "GET")) {
                Long agentID = Long.parseLong(ownedPolls);
                Agent user = agentService.getById(agentID);
                if (user.getOwnedPolls() == null) {
                    throw new IllegalStateException("User with id " + agentID + " has no polls");
                } else {
                    return user.getOwnedPolls();
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @PostMapping
    public Agent createNewAgent(@RequestBody Agent agent, @RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/agents", "POST")) {
                return agentService.createNewAgent(agent);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @DeleteMapping(path = "{id}")
    public void deleteAgent(@PathVariable("id") Long agentID, @RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/agents/{id}", "DELETE")) {
                try{
                    agentService.deleteAgent(agentID);
                    agentService.getById(agentID);
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Agent with ID "+agentID+" was not successfully deleted.");
                } catch (IllegalStateException e) {
                    throw new ResponseStatusException(HttpStatus.OK, "Successfully deleted agent with ID "+agentID);
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @PutMapping(path = "{id}")
    public Agent updateAgent(
            @PathVariable("id") Long agentID,
            @RequestBody String bodyString,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth) {
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/agents/{id}", "DELETE")) {
                return agentService.updateAgent(agentID, bodyString);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }
}
