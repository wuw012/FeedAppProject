package com.hvl.feedApp.messaging;

import com.hvl.feedApp.Poll;
import com.hvl.feedApp.config.MessagingConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.*;

@Component
public class MessageProducer {

    //private RabbitTemplate rabbitTemplate;
    public static final String BINDING_PATTERN_POLL_CREATION = "poll.creation";
    public static final String BINDING_PATTERN_POLL_FINISH = "poll.finish";

    @Bean
    public static void sendMessage(RabbitTemplate rabbitTemplate, String binding_pattern, Poll poll) {
        String message = poll.toString();
        rabbitTemplate.convertAndSend(
                    MessagingConfig.TOPIC_EXCHANGE_NAME,
                    binding_pattern,
                    message
            );
        };
}
