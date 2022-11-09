package com.hvl.feedApp.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hvl.feedApp.Agent;
import com.hvl.feedApp.Poll;
import com.hvl.feedApp.Vote;
import com.hvl.feedApp.config.RabbitMQConfig;
import com.hvl.feedApp.service.AgentService;
import com.hvl.feedApp.service.PollService;
import com.hvl.feedApp.service.VoteService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import javax.naming.AuthenticationNotSupportedException;

@RestController
@RequestMapping(path = "/polls")
public class PollController {
    private final AgentService agentService;
    private final PollService pollService;
    private final VoteService voteService;

    //RabbitTemplate for Messaging upon creating Poll
    private static RabbitTemplate rabbitTemplate;
    private static MessageSendController sendController;

    @Autowired
    public PollController(AgentService agentService, PollService pollService, VoteService voteService) {
        this.agentService = agentService;
        this.pollService = pollService;
        this.voteService = voteService;
    }

    @GetMapping()
    public List<Poll> getPolls(){
        List<Poll> allPolls = pollService.getPolls();
        pollService.refreshPollStatuses(allPolls);
        return allPolls;
    }

    @GetMapping(path = "{pollID}")
    public Poll getPollById(@PathVariable("pollID") Long pollID){
        Poll poll = pollService.getPollById(pollID);
        poll.setStatus();
        return poll;
    }

    @GetMapping(path = "{agentID}/userPolls")
    public List<Poll> getOwnedPolls(@PathVariable("agentID") Long agentID){
        return agentService.getOwnedPolls(agentID);
    }

    @PostMapping("")
    public ResponseEntity<Poll> createNewPoll(@RequestBody Poll poll){
        try {
            // set owner and add poll to owners ownedPolls
            long ownerID = poll.getOwner().getAgentID();
            Agent owner = agentService.getById(ownerID);
            poll.setOwner(owner);
            owner.addOwnedPoll(poll);

            this.rabbitTemplate = new RabbitTemplate();
            sendController = new MessageSendController(rabbitTemplate);
            int yesCount = 0;
            int noCount = 0;
            String question = poll.getQuestion();
            String message = question + " " + yesCount + " " + noCount;
            sendController.sendPollCreationMessage(message);

            return new ResponseEntity<Poll>(pollService.createNewPoll(poll), HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;//IllegalStateException("Something went wrong");
        }
    }

    @DeleteMapping(path = "{pollID}")
    public void deletePoll(@PathVariable("pollID") Long pollID){
        pollService.deletePoll(pollID);
    }

    @PutMapping(path = "{pollID}")
    public Poll updatePoll(@PathVariable("pollID") Long pollID, @RequestBody Poll poll){
        int noCount = poll.getNoCount();
        int yesCount = poll.getYesCount();
        LocalDateTime startTime = poll.getStartTime();
        LocalDateTime endTime = poll.getEndTime();
        boolean isPrivate = poll.isPrivate();
        int pin = poll.getPin();
        String question = poll.getQuestion();
        pollService.updatePoll(pollID, noCount, yesCount, startTime, endTime, isPrivate, pin, question);
        return pollService.getPollById(poll.getPollID());
    }

    // Vote handling
    @PostMapping(path="{pollID}/votes")
    public Vote createVote(
            @PathVariable Long pollID,
            @RequestBody String voteString){
        return voteService.createVote(pollID, voteString);
    }

    @GetMapping(path="{pollID}/votes")
    public List<Vote> getVotes(
            @PathVariable Long pollID
    )
    /*
    Get all votes from poll {pollID}
    :param: pollIO: Long pollID
    :return: List of votes by corresponding pollID
     */
    {
        List<Vote> votes = voteService.getVotesByPollID(pollID);
        return votes;
    }

}
