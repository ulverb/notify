package com.mend.kafka.service;

import com.mend.kafka.dto.ScanDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ScanKafkaProducer {

    @Value("${topic.name.producer}")
    private String scanProducerTopicName;
    private final KafkaTemplate<String, ScanDto> scanKafkaTemplate;

    public ScanKafkaProducer(KafkaTemplate<String, ScanDto> scanKafkaTemplate
    ) {
        this.scanKafkaTemplate = scanKafkaTemplate;
    }

    public void sendScanMessage(ScanDto scan)
    {
        ProducerRecord<String, ScanDto> record = new ProducerRecord<>(scanProducerTopicName, scan);

        try {
            scan.setRetryCount(0);
            scanKafkaTemplate.send(record).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
