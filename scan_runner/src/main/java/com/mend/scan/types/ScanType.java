package com.mend.scan.types;

import lombok.Getter;

@Getter
public enum ScanType {

    RENOVATE(3), // 30 seconds
    SCA(6), // 60 seconds
    SAST(9); //90 seconds

    private final int executionTimeInSeconds;

    ScanType(int executionTimeInSeconds) {
        this.executionTimeInSeconds = executionTimeInSeconds;
    }
}
