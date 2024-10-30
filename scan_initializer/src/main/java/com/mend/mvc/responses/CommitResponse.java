package com.mend.mvc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommitResponse {

    private Long commitId;
    private Long issues;
    private Boolean valid;
}
