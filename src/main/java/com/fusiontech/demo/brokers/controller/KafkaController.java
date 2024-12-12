package com.fusiontech.demo.brokers.controller;

import com.fusiontech.demo.brokers.constant.KafkaTopic;
import com.fusiontech.demo.brokers.dto.KafkaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @PostMapping("/one-message")
  public ResponseEntity<String> sendMessage(@RequestBody KafkaRequest request) {
	kafkaTemplate.send(KafkaTopic.KAFKA_TOPIC, request);
	return ResponseEntity.ok("Сообщение отправлено в Kafka");
  }

  @PostMapping("/messages")
  public ResponseEntity<String> sendMessage(@RequestBody KafkaRequest request,
											@RequestParam int count) {
	for (int i = 0; i < count; i++) {
	  kafkaTemplate.send(KafkaTopic.KAFKA_TOPIC, request);
	  log.info("сообщение отправлено");
	}
	return ResponseEntity.ok("Сообщения отправлены в Kafka");
  }
}
