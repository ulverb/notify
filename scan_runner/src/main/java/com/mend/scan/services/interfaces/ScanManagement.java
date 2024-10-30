package com.mend.scan.services.interfaces;

import com.mend.scan.kafka.dto.ScanDto;

public interface ScanManagement {

    void scanCommit(ScanDto scanDto);
}
