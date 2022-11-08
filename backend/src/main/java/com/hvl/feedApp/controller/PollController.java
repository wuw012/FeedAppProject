package com.hvl.feedApp.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hvl.feedApp.Agent;
import com.hvl.feedApp.Poll;
import com.hvl.feedApp.Vote;
import com.hvl.feedApp.security.Authenticator;
import com.hvl.feedApp.security.Authorizer;
import com.hvl.feedApp.service.AgentService;
import com.hvl.feedApp.service.PollService;
import com.hvl.feedApp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/polls")
public class PollController {
    private final AgentService agentService;
    private final PollService pollService;
    private final VoteService voteService;

    private final Authenticator authenticator;
    private final Authorizer authorizer;

    @Autowired
    public PollController(AgentService agentService, PollService pollService, VoteService voteService) {
        this.agentService = agentService;
        this.pollService = pollService;
        this.voteService = voteService;
        this.authenticator = new Authenticator(agentService);
        this.authorizer = new Authorizer();
    }

    @GetMapping()
    public List<Poll> getPolls(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls", "GET")) {
                List<Poll> allPolls = pollService.getPolls();
                pollService.refreshPollStatuses(allPolls);
                return allPolls;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @GetMapping(path = "{pollID}")
    public Poll getPollById(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @PathVariable("pollID") Long pollID){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls/{pollID}", "GET")) {
                Poll poll = pollService.getPollById(pollID);
                poll.setStatus(poll.getStartTime(),poll.getEndTime());
                return poll;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @GetMapping(path = "{agentID}/userPolls")
    public List<Poll> getOwnedPolls(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @PathVariable("agentID") Long agentID){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls/{agentID]/userPolls", "GET")) {
                return agentService.getOwnedPolls(agentID);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");

    }

    @PostMapping("")
    public ResponseEntity<Poll> createNewPoll(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @RequestBody Poll poll){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls", "POST")) {
                try {
                    // set owner and add poll to owners ownedPolls
                    long ownerID = poll.getOwner().getAgentID();
                    Agent owner = agentService.getById(ownerID);
                    poll.setOwner(owner);
                    owner.addOwnedPoll(poll);

                    return new ResponseEntity<Poll>(pollService.createNewPoll(poll), HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new IllegalStateException("Something went wrong");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @DeleteMapping(path = "{pollID}")
    public void deletePoll(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @PathVariable("pollID") Long pollID){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls/{pollID}", "DELETE")) {
                try{
                    pollService.deletePoll(pollID);
                    pollService.getPollById(pollID);
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Poll with ID "+pollID+" was not successfully deleted.");
                } catch (IllegalStateException e) {
                    throw new ResponseStatusException(HttpStatus.OK, "Successfully deleted Poll with ID "+pollID);
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @PutMapping(path = "{pollID}")
    public Poll updatePoll(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @PathVariable("pollID") Long pollID, @RequestBody Poll poll){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls/{pollID}", "PUT")) {
                int noCount = poll.getNoCount();
                int yesCount = poll.getYesCount();
                LocalDateTime startTime = poll.getStartTime();
                LocalDateTime endTime = poll.getEndTime();
                boolean isPrivate = poll.isPrivate();
                int pin = poll.getPin();
                String question = poll.getQuestion();
                pollService.updatePoll(pollID, noCount, yesCount, startTime, endTime, isPrivate, pin, question);
                return pollService.getPollById(pollID);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    // Vote handling
    @PostMapping(path="{pollID}/batchVote")
    public List<Vote> createBatchVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth, @PathVariable Long pollID, @RequestBody String batchVoteString){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls/{pollID}/batchVote", "POST")) {
                return voteService.createBatchVote(pollID, batchVoteString);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @PostMapping(path="{pollID}/votes")
    public Vote createVote(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth,
            @PathVariable Long pollID,
            @RequestBody String voteString){
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls/{pollID}/votes", "POST")) {
                return voteService.createVote(pollID, voteString);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }

    @GetMapping(path="{pollID}/votes")
    public List<Vote> getVotes(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bAuth,
            @PathVariable Long pollID
    )
    /*
    Get all votes from poll {pollID}
    :param: pollIO: Long pollID
    :return: List of votes by corresponding pollID
     */
    {
        if (authenticator.isAuthenticated(bAuth)) {
            if (authorizer.isAuthorized(authenticator.getUser(), "/polls/{pollID}/votes", "GET")) {
                List<Vote> votes = voteService.getVotesByPollID(pollID);
                return votes;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user credentials");

    }

}
