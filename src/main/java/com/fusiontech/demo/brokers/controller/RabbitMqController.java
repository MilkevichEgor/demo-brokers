package com.fusiontech.demo.brokers.controller;

import com.fusiontech.demo.brokers.constant.RabbitExchange;
import com.fusiontech.demo.brokers.constant.RabbitRoutingKey;
import com.fusiontech.demo.brokers.dto.RabbitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
public class RabbitMqController {

  private final RabbitTemplate rabbitTemplate;

  @PostMapping("/message")
  public ResponseEntity<String> sendMessage(@RequestBody RabbitRequest request) {
	for (int i = 0; i <= 100000; i++) {
	  rabbitTemplate.convertAndSend(RabbitExchange.TEST_EXCHANGE, RabbitRoutingKey.TEST_ROUTING_KEY, request);
	}
	return ResponseEntity.ok("Сообщение отправлено в RabbitMQ");
  }
}
