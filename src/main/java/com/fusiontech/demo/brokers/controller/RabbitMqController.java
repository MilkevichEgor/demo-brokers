package com.fusiontech.demo.brokers.controller;

import com.fusiontech.demo.brokers.constant.RabbitExchange;
import com.fusiontech.demo.brokers.constant.RabbitRoutingKey;
import com.fusiontech.demo.brokers.dto.RabbitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rabbit")
@CrossOrigin
public class RabbitMqController {

  private final RabbitTemplate rabbitTemplate;

  @PostMapping("/one-message")
  public ResponseEntity<String> sendMessage(@RequestBody RabbitRequest request) {
	rabbitTemplate.convertAndSend(RabbitExchange.TEST_EXCHANGE, RabbitRoutingKey.TEST_ROUTING_KEY, request);
	return ResponseEntity.ok("Сообщение отправлено в RabbitMQ");
  }

  @PostMapping("/messages")
  public ResponseEntity<String> sendMessages(@RequestBody RabbitRequest request, @RequestParam int count) {
	for (int i = 0; i < count; i++) {
	  rabbitTemplate.convertAndSend(RabbitExchange.TEST_EXCHANGE, RabbitRoutingKey.TEST_ROUTING_KEY, request);
	}
	return ResponseEntity.ok("Сообщения отправлены в RabbitMQ");
  }
}
