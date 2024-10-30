package com.mend.scan.services;

import com.mend.scan.kafka.dto.ScanDto;
import com.mend.scan.kafka.service.ScanKafkaProducer;
import com.mend.scan.models.IssueModel;
import com.mend.scan.models.ScanModel;
import com.mend.scan.repositories.IssueRepository;
import com.mend.scan.repositories.ScanRepository;
import com.mend.scan.services.interfaces.ScanManagement;
import com.mend.scan.types.ScanStatus;
import com.mend.scan.types.ScanType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("ScanManagementService")
public class ScanManagementService implements ScanManagement {

    private final ScanRepository scanRepository;
    private final IssueRepository issueRepository;
    private final ScanKafkaProducer scanKafkaProducer;

    private final static ModelMapper mapper = new ModelMapper();

    @Override
    public void scanCommit(ScanDto scanDto) {

        Optional<ScanModel> scan = scanRepository.findById(scanDto.getId());

        if(scan.isEmpty()) {
           log.error("Scan record {} for repository {} on branch {} not found and could not be proceeded.",
                    scanDto.getId(), scanDto.getRepositoryName(), scanDto.getBranchName());
           return;
        }

        ScanModel scanModel = scan.get();

        scanModel.setScanStatus(ScanStatus.RUNNING);
        scanRepository.saveAndFlush(scanModel);

        log.info("Initiated {} scan for repository {} on branch {}",
                scanModel.getScanType(), scanModel.getRepositoryName(),
                scanModel.getBranchName());

        List<IssueModel> issueModels = new ArrayList<>();
        // Simulate the scan process by pausing the thread based on ScanType's execution time
        try {
            if (scanModel.getScanType().equals(ScanType.SCA)) {
                for(int i = 0; i < 15; i++) {

                    IssueModel issueModel = new IssueModel();
                    issueModel.setScanId(scanModel.getId());
                    issueModel.setDescription("Issue number: " + i);

                    issueModels.add(issueModel);
                }
                issueRepository.saveAllAndFlush(issueModels);
                Thread.sleep(scanModel.getScanType().getExecutionTimeInSeconds() * 1000L);

            } else if (scanModel.getScanType().equals(ScanType.SAST)) {
                throw new Exception("Simulate failure of the scanId: " + scanModel.getId());
            }

        } catch (Exception e) {

            log.error("Scan {} process for repository {} on branch {} was interrupted: {}",
                     scanModel.getScanType(), scanModel.getRepositoryName(), scanModel.getBranchName(), e.getMessage());

            // Set status as PENDING in case of interruption
            // scanModel.setScanStatus(ScanStatus.PENDING);
            // Now it stays as RUNNING, but it can be changed on PENDING - depends on business definition RUNNING/PENDING
            ScanDto retryScanDto = mapper.map(scanModel, ScanDto.class);
            retryScanDto.setRetryCount(scanDto.getRetryCount() + 1);
            scanKafkaProducer.sendScanMessage(retryScanDto);
            return;
        }

        scanModel.setScanStatus(ScanStatus.COMPLETED);
        scanModel.setIssues((long) issueModels.size());
        scanModel.setValid(issueModels.isEmpty());

        scanRepository.save(scanModel);

        log.info("{} scan completed for repository {} on branch {}", scanModel.getScanType(),
                scanModel.getRepositoryName(), scanModel.getBranchName());
    }
}
