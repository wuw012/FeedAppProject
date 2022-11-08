package com.hvl.feedApp.controller;

import com.hvl.feedApp.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;

@Controller
public class MessageSendController {

    private final RabbitTemplate rabbitTemplate;

    public MessageSendController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPollCreationMessage(String message) {
        String pollMessage = message;
        rabbitTemplate.convertAndSend(RabbitMQConfig.POLL_CREATE, pollMessage);
    }

    public void sendPollFinishMessage(String message) {
        String pollMessage = message;
        //rabbitTemplate.convertAndSend("", "POLL_FINISH", poll.toString);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POLL_FINISH, pollMessage);
    }
    /*
    @PostMapping("/event")
    ResponseEntity<Void> postEventMessage() {
        final String timeNowMessage = String.format("%s - %s", "ExampleMessage", getTimeNowRepresentation());
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEFAULT_QUEUE, timeNowMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getTimeNowRepresentation() {
        long now = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        return simpleDateFormat.format(now);
    }

     */
}