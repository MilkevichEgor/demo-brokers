package com.fusiontech.demo.brokers.config;

import com.fusiontech.demo.brokers.constant.KafkaTopic;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  NewTopic testTopic() {
	return TopicBuilder.name(KafkaTopic.KAFKA_TOPIC)
		.partitions(3)
		.replicas(1)
		.build();
  }

  @Bean
  DefaultKafkaProducerFactory<String, Object> objectProducerFactory(KafkaProperties properties) {
	Map<String, Object> producerProperties = properties.buildProducerProperties(null);
	producerProperties.put("batch.size", 10000);
	producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	return new DefaultKafkaProducerFactory<>(producerProperties);
  }

  @Bean
  KafkaTemplate<String, Object> objectKafkaTemplate(DefaultKafkaProducerFactory<String, Object> objectProducerFactory) {
	return new KafkaTemplate<>(objectProducerFactory);
  }

  @Bean
  public ConsumerFactory<String, Object> kafkaRequestConsumerFactory(KafkaProperties kafkaProperties) {
	Map<String, Object> props = kafkaProperties.buildConsumerProperties(null);
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//	props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.fusiontech.demo.brokers.dto");
	return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public KafkaListenerContainerFactory<?> kafkaRequestListenerFactory(ConsumerFactory<String, Object> kafkaRequestConsumerFactory) {
	ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(kafkaRequestConsumerFactory);
	factory.setConcurrency(3);
	factory.setBatchListener(false);
	return factory;
  }
}
