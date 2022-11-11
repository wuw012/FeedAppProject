package com.hvl.feedApp.messaging;

import com.hvl.feedApp.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessagingConsumer {
    /*

    @RabbitListener(queues = RabbitMQConfig.POLL_CREATE)
    public void pollCreationListener(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        //System.out.println("Message received!: " + message);
        //expiredPolls.add(message);
        //TODO: code for posting to dweet.io (for creating poll)

        System.out.println("Message received upon poll creation!");

        try{
            channel.basicAck(tag, false);
        }catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.POLL_FINISH)
    public void pollFinishListener(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        //System.out.println("Message received!: " + message);
        //expiredPolls.add(message);
        //TODO: code for storing result in NoSQL/MongoDB
        //TODO: code for posting to dweet.io (for closing poll)
        System.out.println("Message received upon poll closure!");

        //Two different topics/queues, one for when a poll is created (and posting to dweet.io),
        //and one for when a poll is closed (and both storing in NoSQL and posting to dweet.io)

        try{
            channel.basicAck(tag, false);
        }catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }

     */
}
