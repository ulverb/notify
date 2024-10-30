package com.mend.scan.kafka.service;

import com.mend.scan.kafka.dto.ScanDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScanKafkaProducer
{
    @Value("${topic.name.producer}")
    private String scanProducerTopicName;
    private final KafkaTemplate<String, ScanDto> scanKafkaTemplate;

    public void sendScanMessage(ScanDto scan)
    {
        ProducerRecord<String, ScanDto> record = new ProducerRecord<>(scanProducerTopicName, scan);
            scanKafkaTemplate.send(record);
    }
}
