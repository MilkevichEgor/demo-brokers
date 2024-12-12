package com.fusiontech.demo.brokers.config;


import static com.fusiontech.demo.brokers.constant.RabbitRoutingKey.TEST_ROUTING_KEY;

import com.fusiontech.demo.brokers.constant.RabbitExchange;
import com.fusiontech.demo.brokers.constant.RabbitQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Bean
  public Queue queueTest() {
	return new Queue(RabbitQueue.TEST_QUEUE);
  }

  @Bean
  public DirectExchange exchange() {
	return new DirectExchange(RabbitExchange.TEST_EXCHANGE);
  }

  @Bean
  public Binding bindingRegister() {
	return BindingBuilder
		.bind(queueTest())
		.to(exchange())
		.with(TEST_ROUTING_KEY);
  }

  @Bean
  public Jackson2JsonMessageConverter jsonMessageConverter() {
	return new Jackson2JsonMessageConverter();
  }
}
