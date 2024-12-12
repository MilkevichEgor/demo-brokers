package com.fusiontech.demo.brokers.controller;

import com.fusiontech.demo.brokers.constant.KafkaTopic;
import com.fusiontech.demo.brokers.dto.KafkaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @PostMapping("/message")
  public ResponseEntity<String> sendMessage(@RequestBody KafkaRequest request) {
    for (int i = 0; i <= 100000; i++) {
      log.info("сообщение отправлено");
      kafkaTemplate.send(KafkaTopic.KAFKA_TOPIC, request);
    }
    return ResponseEntity.ok("Сообщение отправлено в Kafka");
  }
}
