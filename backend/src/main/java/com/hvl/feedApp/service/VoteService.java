package com.hvl.feedApp.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hvl.feedApp.Agent;
import com.hvl.feedApp.Poll;
import com.hvl.feedApp.repository.PollRepository;
import com.hvl.feedApp.repository.VoteRepository;
import com.hvl.feedApp.Vote;
import com.hvl.feedApp.service.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import com.hvl.feedApp.controller.PollController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository){
        this.voteRepository = voteRepository;
    }

    @Autowired
    AgentService agentService;

    @Autowired
    PollService pollService;

    public List<Vote> getVotes(){
        return voteRepository.findAll();
    }

    public Vote getVoteById(Long voteID){
        return voteRepository.findById(voteID).orElseThrow(() -> new IllegalStateException("Vote with id: "+ voteID + " does not exist"));
    }

    public List<Vote> getVotesByPollID(Long pollID){
        List<Vote> votes = voteRepository.findAll();

        List<Vote> voteByPollID = new ArrayList<Vote>();

        for(Vote vote : votes){
            if (vote.getPoll().getPollID() == pollID) {
                voteByPollID.add(vote);
            }
        }

        return voteByPollID;
    }

    public List<Vote> getVotesByAgentID(Long agentID){
        List<Vote> votes = voteRepository.findAll();//TODO: findBy map to preserve memory

        List<Vote> votesByAgentID = new ArrayList<Vote>();

        for(Vote vote : votes){
            if (vote.getVoter().getAgentID() == agentID) {
                votesByAgentID.add(vote);
            }
        }
        return votesByAgentID;
    }

    public Vote createVote(Long pollID, String voteString){
        JsonObject voteJson = new Gson().fromJson(voteString, JsonObject.class);

        //TODO: Error handling, field validation!
        try {
            String voterUsername = voteJson.get("voter_username").getAsString(); // "voter_id":2,
            boolean answerYes = voteJson.get("answer_yes").getAsBoolean();

            Agent voter = agentService.getByUsername(voterUsername);
            Poll poll = pollService.getPollById(pollID);

            // increment or decrement poll answer count
            if (answerYes) {
                poll.setYesCount(poll.getYesCount() + 1);
            } else {
                poll.setNoCount(poll.getNoCount() + 1);
            }

            if (voter != null){
                Vote vote = new Vote(answerYes, voter, poll);
                voteRepository.save(vote);
                return vote;
            }
            throw new ResponseStatusException(HttpStatus.OK, "Anonymous vote added to poll");

        } catch (Exception e){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Vote> createBatchVote(Long pollID, String batchVoteString){
        JsonObject batchVoteJson = new Gson().fromJson(batchVoteString, JsonObject.class);

        List<Boolean> voteList = new ArrayList<>();
        Long deviceID = batchVoteJson.get("voter_id").getAsLong();
        JsonArray votes = batchVoteJson.get("votes").getAsJsonArray();

        Agent device = agentService.getById(deviceID);
        Poll poll = pollService.getPollById(pollID);

        if (votes != null){
            for (int i = 0; i < votes.size(); i++) {
                voteList.add(votes.get(i).getAsBoolean());
            }
        }
        // a list of vote objects to be returned
        List<Vote> voteCollect = new ArrayList<>();
        for (boolean answerYes : voteList){
            if (answerYes){
                poll.setYesCount(poll.getYesCount()+1);
                Vote yesVote =  new Vote(true, device, poll);
                voteCollect.add(yesVote);
                voteRepository.save(yesVote);
            } else if (!answerYes){
                poll.setNoCount(poll.getNoCount()+1);
                Vote noVote =  new Vote(false, device, poll);
                voteCollect.add(noVote);
                voteRepository.save(noVote);
            }

        } return voteCollect;
    }

    public void deleteVote(Long voteID){
        boolean exists = voteRepository.existsById(voteID);
        if (!exists){
            throw new IllegalStateException("Vote with id: "+ voteID + " does not exist");
        }
        voteRepository.deleteById(voteID);
    }
}
