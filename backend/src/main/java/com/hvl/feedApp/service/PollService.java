package com.hvl.feedApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hvl.feedApp.Enums.Status;
import com.hvl.feedApp.Poll;
import com.hvl.feedApp.Vote;
import com.hvl.feedApp.config.MessagingConfig;
import com.hvl.feedApp.repository.PollRepository;
import com.hvl.feedApp.repository.VoteRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    public static final String BINDING_PATTERN_POLL_CREATION = ".pollcreation.";
    public static final String BINDING_PATTERN_POLL_FINISH = ".pollfinish.";
    private static RabbitTemplate rabbitTemplate;

    @Autowired
    public PollService(PollRepository pollRepository, VoteRepository voteRepository, RabbitTemplate rabbitTemplate) {
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Poll> getPolls(){
        List<Poll> polls = pollRepository.findAll();
        return polls;
    }

    public Poll getPollById(Long pollID){
        Poll poll = pollRepository.findById(pollID).orElseThrow(() -> new IllegalStateException("Poll with id: "+ pollID + " does not exist"));
        refreshPollStatus(poll);
        return poll;
    }

    public Poll createNewPoll(Poll poll) {
        poll.setPin(0);/*
        if (!poll.isPrivate()) {
            poll.setPin(0);
        } else if (poll.isPrivate() && poll.getPin() == 0) {
            throw new IllegalStateException("Private polls must have a pincode");
        }*/


        if (poll.getEndTime().isBefore(LocalDateTime.now())) {

            throw new IllegalStateException("Cannot create expired Poll with datetime "+poll.getEndTime());
        }
        Poll result = pollRepository.save(poll);

        return result;
    }

    public void refreshPollStatuses(List<Poll> allPolls) throws InterruptedException {
        Runnable runnable =
                () -> {
            for (Poll poll : allPolls){
                // Dweet requires 1 second of sleep between each post
                try {
                    Thread.sleep(1100);
                    refreshPollStatus(poll);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void refreshPollStatus(Poll poll) {
        Status status = poll.getStatus();
        try {
            this.sendMessage(poll);
        }catch (Exception e) {
            System.out.println("Update for Poll "+poll.getPollID()+" failed. Reason: "+e);
        }
    }

    public void sendMessage(Poll poll) throws IOException, InterruptedException {
        Status pollStatus = poll.getStatus();
        if (messageNecessary(pollStatus, poll.getExpirationSent(), poll.getActiveSent())) {
            ObjectMapper mapper = new ObjectMapper();
            //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            //mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
            mapper.registerModule(new JavaTimeModule());


            String bindingPattern;
            if (pollStatus == Status.EXPIRED){
                bindingPattern = BINDING_PATTERN_POLL_FINISH;
                poll.setExpirationSent();

            }else{
                bindingPattern = BINDING_PATTERN_POLL_CREATION;
                poll.setActiveSent();
            }

            pollRepository.save(poll);

            // Post to dweet
            publishToDweet(poll);

            // Publish to messaging
            String pollJson = mapper.writeValueAsString(poll);
            rabbitTemplate.convertAndSend(
                    MessagingConfig.TOPIC_EXCHANGE_NAME,
                    bindingPattern,
                    pollJson
            );
        }
    }

    private static boolean messageNecessary(Status pollStatus, boolean expiredSent, boolean activeSent) {
        if ((pollStatus == Status.ACTIVE) && (!activeSent)) {
            return true;
        }
        if((pollStatus == Status.EXPIRED) && (!expiredSent)) {
            return true;
        }
        return false;
    }


    public void publishToDweet(Poll poll) throws IOException, InterruptedException {
        // Serialize Poll object to Json
        ObjectMapper mapper = new ObjectMapper();
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        mapper.registerModule(new JavaTimeModule());
        String pollJson = mapper.writeValueAsString(poll);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dweet.io/dweet/for/feed-app-group11-poll"+poll.getPollID()))
                .POST(HttpRequest.BodyPublishers.ofString(pollJson))//poll.toString()))
                .header("content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Dweet response: "+response.body());
    }
/*    public boolean isDweetPublishEvent(Status originalStatus, Status updatedStatus) {
        if (!(originalStatus==Status.EXPIRED)) {
            switch(originalStatus){
                case FUTURE:
                    if (updatedStatus == Status.ACTIVE){
                        return true;
                    }
                case ACTIVE:
                    if (updatedStatus == Status.EXPIRED){
                        return true;
                    }
            }
        }
        return false;
    }*/

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
