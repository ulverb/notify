package com.mend.kafka.config;

import com.mend.kafka.dto.ScanDto;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerProducerListener;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    @Autowired private MeterRegistry registry;

    public KafkaProducerConfig(MeterRegistry registry) {
        this.registry = registry;
    }

    @Bean
    public ProducerFactory<String, ScanDto> scanProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<String, ScanDto> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
        producerFactory.addListener(new MicrometerProducerListener<>(registry));
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, ScanDto> scanKafkaTemplate(ProducerFactory<String, ScanDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
