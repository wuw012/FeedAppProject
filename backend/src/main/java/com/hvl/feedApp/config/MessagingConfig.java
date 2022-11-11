package com.hvl.feedApp.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange";

    public static final String TOPIC_QUEUE_1_NAME = "topic.queue1";
    public static final String TOPIC_QUEUE_2_NAME = "topic.queue2";


    @Bean
    public Declarables topicBindings() {
        Queue topicQueue1 = new Queue(TOPIC_QUEUE_1_NAME, false);
        Queue topicQueue2 = new Queue(TOPIC_QUEUE_2_NAME, false);

        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, false, false);

        return new Declarables(
                topicQueue1,
                topicQueue2,
                topicExchange,
                BindingBuilder
                        .bind(topicQueue1)
                        .to(topicExchange)
                        .with("*.pollcreation.*"),
                BindingBuilder
                        .bind(topicQueue2)
                        .to(topicExchange)
                        .with("*.pollfinish.*")
        );
    }

}