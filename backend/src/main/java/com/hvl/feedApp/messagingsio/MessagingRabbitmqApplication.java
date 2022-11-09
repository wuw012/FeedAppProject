package com.hvl.feedApp.messagingsio;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagingRabbitmqApplication {

	static final String topicExchangeName = "poll-messaging-exchange";

	static final String queue1PollCreate = "poll-creation-queue";
	static final String queue2PollFinish = "poll-finish-queue";

	@Bean
	Queue queue1() {
		return new Queue(queue1PollCreate, false);
	}

	@Bean
	Queue queue2() {
		return new Queue(queue2PollFinish, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding1(Queue queue1, TopicExchange exchange) {
		return BindingBuilder.bind(queue1).to(exchange).with("poll.create.#");
	}

	@Bean
	Binding binding2(Queue queue2, TopicExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with("poll.finish.#");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queue1PollCreate, queue2PollFinish);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MessagingRabbitmqApplication.class, args).close();
	}

}
