package com.hvl.feedApp.messaging;

import com.hvl.feedApp.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {


    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange";

    public static final String TOPIC_QUEUE_1_NAME = "topic.queue1";
    public static final String TOPIC_QUEUE_2_NAME = "topic.queue2";

    public static final String BINDING_PATTERN_POLL_CREATION = "poll.creation";
    public static final String BINDING_PATTERN_POLL_FINISH = "poll.finish";

    @RabbitListener(queues = MessagingConfig.TOPIC_QUEUE_1_NAME)
    public void consumeMessageFromTopicQueue1(String message) {
        //poll creation, code for posting to dweet.io goes here
    }

    @RabbitListener(queues = MessagingConfig.TOPIC_QUEUE_2_NAME)
    public void consumeMessageFromTopicQueue2(String message) {
        //poll finish, code for posting to dweet.io and storing to mongodb goes here
    }

}