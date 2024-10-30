package com.mend.mvc.responses;

import com.mend.dal.types.ScanStatus;
import com.mend.dal.types.ScanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScanResponse {

    private ScanType scanType;
    private ScanStatus scanStatus;
    private Long userId;
    private String username;
    private String organizationName;
    private String repositoryName;
    private String branchName;
    private Long commitId;
    private Long issues;
    private Boolean valid;
    private String message;
    private Date updatedAt;
}
