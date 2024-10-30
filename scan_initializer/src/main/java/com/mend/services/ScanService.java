package com.mend.services;

import com.mend.dal.models.ScanModel;
import com.mend.dal.repositories.ScanRepository;
import com.mend.dal.repositories.UserRepository;
import com.mend.dal.types.ScanStatus;
import com.mend.kafka.dto.ScanDto;
import com.mend.kafka.service.ScanKafkaProducer;
import com.mend.mvc.requests.ScanRequest;
import com.mend.mvc.responses.ScanResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@AllArgsConstructor
@Service("ScanService")
public class ScanService
{
    private final ScanKafkaProducer scanKafkaProducer;
    private final ScanRepository scanRepository;
    private final UserRepository userRepository;

    private final static ModelMapper mapper = new ModelMapper();

    @Transactional
    public ScanResponse initializeScan(ScanRequest scan)
    {
        ScanModel scanModel = mapper.map(scan, ScanModel.class);
        long userId = userRepository.findByUsername(scan.getUsername()).orElseThrow().getId();
        scanModel.setScanStatus(ScanStatus.PENDING);
        scanModel.setUserId(userId);
        ScanModel responseModel = scanRepository.saveAndFlush(scanModel);
        ScanDto scanDto = mapper.map(responseModel, ScanDto.class);

        scanKafkaProducer.sendScanMessage(scanDto);

        return mapper.map(responseModel, ScanResponse.class);
    }

    public Long countPendingScans()
    {
        return scanRepository.countByScanStatus(ScanStatus.PENDING);
    }

    public ScanResponse getScanInfoByCommitId(Long commitId)
    {
        Optional<ScanModel> scan = scanRepository.findLatestCompletedScanByCommitId(commitId);

        if(scan.isEmpty()) {
            log.error("Scan record for commitId={} doesn't exists.", commitId);
            return null;
        }

        return mapper.map(scan.get(), ScanResponse.class);
    }
}
