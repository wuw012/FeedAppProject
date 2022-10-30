package com.hvl.feedApp.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.DeliverCallback;

public class ____EmitPollUpdates {

    private static String EXCHANGE_NAME;

    public ____EmitPollUpdates() {
    }

    public ____EmitPollUpdates(String exchange_name) {
        this.EXCHANGE_NAME = exchange_name;

    }

    public void addMessage(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            System.out.println("Error creating connection");
        }
    }
}
