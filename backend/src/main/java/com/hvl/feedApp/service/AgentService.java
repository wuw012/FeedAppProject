package com.hvl.feedApp.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hvl.feedApp.Agent;
import com.hvl.feedApp.Enums.Role;
import com.hvl.feedApp.Poll;
import com.hvl.feedApp.Vote;
import com.hvl.feedApp.repository.AgentRepository;
import com.hvl.feedApp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.transaction.Transactional;
import java.util.*;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final VoteRepository voteRepository;


    private static final Set<String> allowedRoles = new HashSet<String>(Arrays.asList("USER", "ADMIN", "DEVICE"));

    @Autowired
    public AgentService(AgentRepository agentRepository, VoteRepository voteRepository) {
        this.agentRepository = agentRepository;
        this.voteRepository = voteRepository;
    }

    public List<Agent> getAgents() {
        return agentRepository.findAll();
    }

    public Agent getById(Long agentID){
        return agentRepository.findById(agentID).orElseThrow(() -> new IllegalStateException("Vote with id: "+ agentID + " does not exist"));
    }

    public Boolean exists(String username) {
        try {
            Agent agent = this.getByUsername(username);
            return true;
        }catch (ResponseStatusException e){
            return false;
        }
    }
    public Agent createNewUser(Agent agent) {
        agent.setRole(Role.USER);
        return createNewAgent(agent);
    }
    public Agent createNewAgent(Agent agent) {
        String username = agent.getUsername();
        if (this.exists(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with username "+username+" already exists.");
        }
        agentRepository.save(agent);
        return this.getById(agent.getAgentID());
    }

    public List<Poll> getOwnedPolls(String username){
        Agent user =  agentRepository.getByUsername(username);
        if (user == null) {
            throw new IllegalStateException("User with username: "+ username + " does not exist");
        }
        return user.getOwnedPolls();
    }

    public void deleteAgent(Long agentID) {
        // Find agent
        Boolean exists = agentRepository.existsById(agentID);

        if (!exists) {
            throw new IllegalStateException("User with id: " + agentID + " does not exist");
        }
        Agent agent = this.getById(agentID);

        // Find related votes
        List<Vote> agentsVotes = voteRepository.findAll();
        for (Vote vote : agentsVotes) {
            if (vote.getVoter().getAgentID()  == agent.getAgentID()) {
                voteRepository.delete(vote);
            }
        }

        agentRepository.deleteById(agentID);
    }

    public Agent getByUsername(String username) {
        Agent agent = agentRepository.getByUsername(username);
        if (agent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find Agent with username "+username);
        }else return agent;
    }

    // TODO: Abstract to field validation function and reuse on create
    @Transactional
    public Agent updateAgent(Long agentID, String bodyString) {
        Agent agent = agentRepository.findById(agentID).orElseThrow(() -> new IllegalStateException("User with id: " + agentID + " does not exist"));

        JsonObject body = new Gson().fromJson(bodyString, JsonObject.class);

        //TODO: Error handling, field validation!
        String username = body.get("username").getAsString();
        String email = body.get("email").getAsString();
        String password = body.get("password").getAsString();
        String role = body.get("role").getAsString();

        if (username != null && username.length() > 0) {
            agent.setUsername(username);
        }
        if (email != null && email.length() > 0) {
            agent.setEmail(email);
        }
        if (password != null) {//&& password.length() > 6) {
            agent.setPassword(password);
        }
//        else {
//            throw new IllegalStateException("Password must be a string of characters longer than 6");
//        }
        if (role != null && allowedRoles.contains(role)) {
            agent.setRole(Role.valueOf(role));
        }
        else {
            throw new IllegalStateException("Role must be one of three strings: 'USER', 'ADMIN' or 'DEVICE', not: "+role);
        }

        return this.getById(agentID);

    }
}
