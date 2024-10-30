package com.mend.scan.kafka.service;


import com.mend.scan.kafka.dto.ScanDto;
import com.mend.scan.models.ScanModel;
import com.mend.scan.repositories.ScanRepository;
import com.mend.scan.services.ScanManagementService;
import com.mend.scan.types.ScanStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@RequiredArgsConstructor
@Service
public class ScanConsumerService
{
    public static final Long MAX_KAFKA_RETRIES = 2L;

    private final static ModelMapper mapper = new ModelMapper();

    private final ScanManagementService scanManagementService;
    private final ScanRepository scanRepository;

    @KafkaListener(topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "scanKafkaListenerContainerFactory",
            concurrency = "5")
    public void scanConsumer(ConsumerRecord<String, ScanDto> record){

        if (record.value().getRetryCount() < MAX_KAFKA_RETRIES){
            scanManagementService.scanCommit(record.value());
        } else {
            ScanModel scanModel = mapper.map(record.value(), ScanModel.class);
            scanModel.setScanStatus(ScanStatus.FAILED);
            scanRepository.saveAndFlush(scanModel);
        }
    }
}
