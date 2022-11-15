package com.hvl.feedApp.controller;

import com.hvl.feedApp.Vote;
import com.hvl.feedApp.security.Authenticator;
import com.hvl.feedApp.security.Authorizer;
import com.hvl.feedApp.service.AgentService;
import com.hvl.feedApp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/vote")
public class VoteController {

    private final VoteService voteService;
    private final AgentService agentService;
    private final Authenticator authenticator;
    private final Authorizer authorizer;

    @Autowired
    public VoteController(VoteService voteService, AgentService agentService){
        this.voteService = voteService;
        this.agentService = agentService;
        this.authenticator = new Authenticator(agentService);
        this.authorizer = new Authorizer();
    }
    @GetMapping
    public List<Vote> getVotes(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/vote", "GET")) {
                return voteService.getVotes();
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @GetMapping(path = "{voteID}")
    public Vote getVoteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @PathVariable("voteID") Long voteID){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/vote/{voteID}", "GET")) {
                return voteService.getVoteById(voteID);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");

    }

    @DeleteMapping(path = "{voteID}")
    public void deleteVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @PathVariable("voteID") Long voteID){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/vote/{voteID}", "DELETE")) {
                try{
                    voteService.deleteVote(voteID);
                    voteService.getVoteById(voteID);
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Vote with ID "+voteID+" was not successfully deleted.");
                } catch (IllegalStateException e) {
                    throw new ResponseStatusException(HttpStatus.OK, "Successfully deleted Vote with ID "+voteID);
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }
}
