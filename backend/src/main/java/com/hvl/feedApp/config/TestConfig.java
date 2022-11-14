package com.hvl.feedApp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hvl.feedApp.repository.AgentRepository;
import com.hvl.feedApp.repository.VoteRepository;
import com.hvl.feedApp.repository.PollRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hvl.feedApp.Poll;
import com.hvl.feedApp.Agent;
import com.hvl.feedApp.Vote;
import com.hvl.feedApp.Enums.Role;


@Configuration
public class TestConfig {
    @Bean
    CommandLineRunner userCommandLineRunner(AgentRepository aRepository, PollRepository pRepository, VoteRepository vRepository) {
        return args -> {
            Agent agent007 = new Agent(
                    "agent_007",
                    "agent_007@mail.com",
                    "fa585d89c851dd338a70dcf535aa2a92fee7836dd6aff1226583e88e0996293f16bc009c652826e0fc5c706695a03cddce372f139eff4d13959da6f1f5d3eabe",//"12345678",
                    Role.ADMIN);

            Agent Geir = new Agent(
                    "69geir420",
                    "geir420@mail.com",
                    "de8f60746ada0483c88cb6578b66f194b406390209a42c51c4ae3c21b030b4daff434bd8e00bda79d3588201263e36ccf73eb32138b37cfacffcff1efe7d163d",//"totallyhashedandencryptedpassword",
                    Role.USER);

            Agent Bob = new Agent(
                    "bobleif",
                    "bobleif@gmail.com",
                    "de8f60746ada0483c88cb6578b66f194b406390209a42c51c4ae3c21b030b4daff434bd8e00bda79d3588201263e36ccf73eb32138b37cfacffcff1efe7d163d",//",
                    Role.USER);


            Poll testPoll = new Poll();
            testPoll.setQuestion("Is this a current question?");
            testPoll.setOwner(Bob);
            testPoll.setStartTime(LocalDateTime.now());
            testPoll.setEndTime(LocalDateTime.now().plusDays(1));
            testPoll.setPrivate(true);


            Poll testExpiredPoll = new Poll();
            testExpiredPoll.setQuestion("Is this a past question?");
            testExpiredPoll.setOwner(Bob);
            testExpiredPoll.setStartTime(LocalDateTime.now().minusDays(2));
            testExpiredPoll.setEndTime(LocalDateTime.now().minusDays(1));

            Poll testFuturePoll = new Poll();
            testFuturePoll.setQuestion("Is this a future question?");
            testFuturePoll.setOwner(Bob);
            testFuturePoll.setStartTime(LocalDateTime.now().plusDays(1));
            testFuturePoll.setEndTime(LocalDateTime.now().plusDays(2));

           // List<Vote> bobsVotes = new ArrayList<Vote>();
            Vote bobsVote = new Vote();
            bobsVote.setPoll(testPoll);
            bobsVote.setVoter(Bob);
            bobsVote.setAnswer(true);
            testPoll.setYesCount(testPoll.getYesCount()+1);

            Vote bobsVote1 = new Vote();
            bobsVote1.setPoll(testExpiredPoll);
            bobsVote1.setVoter(Bob);
            bobsVote1.setAnswer(true);
            testPoll.setYesCount(testPoll.getYesCount()+1);

/*            bobsVotes.add(bobsVote);
            bobsVotes.add(bobsVote1);
            Bob.setGivenVotes(bobsVotes);*/

            Vote geirsVote = new Vote();
            geirsVote.setPoll(testPoll);
            geirsVote.setVoter(Geir);
            geirsVote.setAnswer(true);
            testPoll.setYesCount(testPoll.getYesCount()+1);

            ArrayList<Poll> bobsPolls = new ArrayList<Poll>();
            bobsPolls.add(testPoll);
            bobsPolls.add(testFuturePoll);
            bobsPolls.add(testExpiredPoll);
            Bob.setOwnedPolls(bobsPolls);

            aRepository.saveAll(List.of(Geir,Bob,agent007));
            pRepository.saveAll(List.of(testPoll, testFuturePoll, testExpiredPoll));
            vRepository.saveAll(List.of(bobsVote, bobsVote1, geirsVote));

        };
    }
}
