package com.fusiontech.demo.brokers.service;

import com.fusiontech.demo.brokers.constant.RabbitQueue;
import com.fusiontech.demo.brokers.dto.RabbitRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitConsumerService {

  @RabbitListener(queues = RabbitQueue.TEST_QUEUE)
  public void getMessage(@Payload RabbitRequest request) {
	log.info("Сообщение {}, получено", request.message());
  }
}
