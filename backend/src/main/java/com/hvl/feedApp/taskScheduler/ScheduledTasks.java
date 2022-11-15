package com.hvl.feedApp.taskScheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hvl.feedApp.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private final PollService pollService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public ScheduledTasks(PollService pollService) {
        this.pollService = pollService;
    }

    @Scheduled(fixedRate = 15000)
    public void updatePolls() throws InterruptedException {
        pollService.refreshPollStatuses(pollService.getPolls());
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}