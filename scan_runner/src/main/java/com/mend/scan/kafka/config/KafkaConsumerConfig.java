package com.mend.scan.kafka.config;

import com.mend.scan.kafka.dto.ScanDto;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;
    @Value(value="${spring.kafka.consumer.group-id}")
    private String groupId;
    @Autowired MeterRegistry registry;

    @Bean
    public ConsumerFactory<String, ScanDto> scanConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.mend.scan.kafka.dto.ScanDto");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS,false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        // Adding the specific parameters
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "9000"); // 9 seconds
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "180000"); // 3 minutes
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "1200000"); // 20 minutes
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "900000"); // 15 minutes
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10"); // Limit to 10 records per poll
        ConsumerFactory<String, ScanDto> consumer = new DefaultKafkaConsumerFactory<>(props);
        consumer.addListener(new MicrometerConsumerListener<>(registry));
        return consumer;

    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ScanDto> scanKafkaListenerContainerFactory(ConsumerFactory<String, ScanDto> scanDtoConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, ScanDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(scanDtoConsumerFactory);
        return factory;
    }
}