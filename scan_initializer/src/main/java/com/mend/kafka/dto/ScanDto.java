package com.mend.kafka.dto;

import com.mend.dal.types.ScanStatus;
import com.mend.dal.types.ScanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
public class ScanDto implements Serializable
{

    private Long id;
    private Long userId;
    private String organizationName;
    private String repositoryName;
    private String branchName;
    private Long commitId;
    private ScanStatus scanStatus;
    private ScanType scanType;
    private Date createdAt;
    private int retryCount;

}
