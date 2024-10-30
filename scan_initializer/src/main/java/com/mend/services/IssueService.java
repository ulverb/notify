package com.mend.services;

import com.mend.dal.repositories.IssueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service("IssueService")
public class IssueService
{
    private final IssueRepository issueRepository;

    public Long countIssuesFound(String username)
    {
        // Queries count issues with different commitIds
        // to avoid counting identical issues
        // that were saved by different scans on the same commitId.
        if (!username.equals("ALL")) {
            return issueRepository.countIssuesWithoutDuplicatedScansByUsername(username);

            // Count all issues include duplicated scans on same commitId for specified username.
            // return issueRepository.countAllIssuesByUsername(username);
        } else {

            return issueRepository.countIssuesWithoutDuplicatedScansFofAllUsers();
            // Count all issues for ALL users include duplicated scans on same commitId.
            // return issueRepository.count();
        }
    }
}
