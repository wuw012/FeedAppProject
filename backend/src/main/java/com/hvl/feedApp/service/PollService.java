package com.hvl.feedApp.service;

import com.hvl.feedApp.Poll;
import com.hvl.feedApp.Vote;
import com.hvl.feedApp.repository.PollRepository;
import com.hvl.feedApp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public PollService(PollRepository pollRepository, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
    }

    public List<Poll> getPolls(){
        return pollRepository.findAll();
    }

    public Poll getPollById(Long pollID){
        return pollRepository.findById(pollID).orElseThrow(() -> new IllegalStateException("Poll with id: "+ pollID + " does not exist"));
    }

    public Poll createNewPoll(Poll poll) {
        if (!poll.isPrivate()) {
            poll.setPin(0);
        } else if (poll.isPrivate() && poll.getPin() == 0) {
            throw new IllegalStateException("Private polls must have a pincode");
        }
        if (poll.getEndTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot create expired Poll with datetime "+poll.getEndTime());
        }
        poll.setStatus(poll.getStartTime(), poll.getEndTime());
        return pollRepository.save(poll);
    }

    public void refreshPollStatuses(List<Poll> allPolls){
        for (Poll poll : allPolls){
            poll.setStatus(poll.getStartTime(),poll.getEndTime());
        }

    }

    public void deletePoll(Long pollID) {
        boolean exists = pollRepository.existsById(pollID);
        if (!exists){
            throw new IllegalStateException("Poll with id: "+ pollID + " does not exist");
        }
        Poll poll = this.getPollById(pollID);

        // Find related votes
        List<Vote> pollVotes = voteRepository.findAll();
        for (Vote vote : pollVotes) {
            if (vote.getPoll().getPollID() == poll.getPollID()) {
                voteRepository.delete(vote);
            }
        }

        pollRepository.deleteById(pollID);
    }

    @Transactional
    public void updatePoll(Long pollID, int noCount, int yesCount, LocalDateTime startTime, LocalDateTime endTime, boolean isPrivate, int pin, String question) {
        Poll poll = pollRepository.findById(pollID).orElseThrow(() -> new IllegalStateException("Poll with id: "+ pollID + " does not exist"));
        poll.setNoCount(noCount);
        poll.setYesCount(yesCount);
        poll.setPrivate(isPrivate);
        poll.setPin(pin);
        if (question != null && question.length()>0) {
            poll.setQuestion(question);
        }
    }
}
