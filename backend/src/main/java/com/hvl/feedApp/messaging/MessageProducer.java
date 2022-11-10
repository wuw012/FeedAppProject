package com.hvl.feedApp.messaging;

import com.hvl.feedApp.Poll;
import com.hvl.feedApp.config.MessagingConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    public static final String BINDING_PATTERN_POLL_CREATION = "poll.creation";
    public static final String BINDING_PATTERN_POLL_FINISH = "poll.finish";

    @Bean
    public static void sendMessage(RabbitTemplate rabbitTemplate, Poll poll, String message) {
        rabbitTemplate.convertAndSend(
                    MessagingConfig.TOPIC_EXCHANGE_NAME,
                    BINDING_PATTERN_POLL_CREATION,
                    message
            );
        rabbitTemplate.convertAndSend(
                    MessagingConfig.TOPIC_EXCHANGE_NAME,
                    BINDING_PATTERN_POLL_FINISH,
                    message
            );
        };
}
