package com.mend.mvc.requests;

import com.mend.dal.types.ScanType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScanRequest {
    @NotBlank(message = "username")
    private String username;

    @NotBlank(message = "organizationName")
    private String organizationName;

    @NotBlank(message = "repositoryName")
    private String repositoryName;

    @NotBlank(message = "branchName")
    private String branchName;

    @NotBlank(message = "commitId")
    private Long commitId;

    @NotBlank(message = "scanType")
    private ScanType scanType;
}
