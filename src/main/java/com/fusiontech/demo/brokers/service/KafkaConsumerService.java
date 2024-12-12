package com.fusiontech.demo.brokers.service;

import com.fusiontech.demo.brokers.constant.KafkaGroupId;
import com.fusiontech.demo.brokers.constant.KafkaTopic;
import com.fusiontech.demo.brokers.dto.KafkaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

  @KafkaListener(topics = KafkaTopic.KAFKA_TOPIC, groupId = KafkaGroupId.KAFKA_GROUP_ID, concurrency = "3")
  public void processMessage(@Payload KafkaRequest message,
							 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
							 @Header(KafkaHeaders.OFFSET) long offset) {
	log.info("Сообщение: {}, партиция: {}, оффсет: {}, поток: {}",
		message, partition, offset, Thread.currentThread().getName());
  }
}
