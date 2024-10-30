package com.mend.mvc.controllers;

import com.mend.metrics.annotations.CountRequests;
import com.mend.mvc.requests.ScanRequest;
import com.mend.mvc.requests.UserRequest;
import com.mend.mvc.responses.ScanResponse;
import com.mend.mvc.responses.UserResponse;
import com.mend.services.IssueService;
import com.mend.services.ScanService;
import com.mend.services.UserStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/scans")
public class ScanController
{

    @Autowired private final ScanService scanService;
    @Autowired private final UserStatisticsService userStatisticsService;
    @Autowired private final IssueService issueService;

    @CountRequests
    @PostMapping("/users/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest user
    ) {
        // Assume request is valid;
        return ResponseEntity.ok(userStatisticsService.createUser(user));
    }

    @CountRequests
    @PostMapping("/initiate")
    public ResponseEntity<ScanResponse> initiateScan(
            @RequestBody ScanRequest scan
    ) {
        // Assume request is valid;
        return ResponseEntity.ok(scanService.initializeScan(scan));
    }

    @CountRequests
    @GetMapping("/pending-count")
    public ResponseEntity<Long> getPendingScansCount()
    {
        long pendingCount = scanService.countPendingScans();
        return ResponseEntity.ok(pendingCount);
    }

    @CountRequests
    @GetMapping("/issues-count/{username}")
    public ResponseEntity<Long> getTotalIssuesFound(
            @PathVariable("username") String username
    ) {
        Long issuesCount = issueService.countIssuesFound(username);
        return ResponseEntity.ok(issuesCount);
    }

    @CountRequests
    @GetMapping("/users/top-active")
    public ResponseEntity<List<UserResponse>> getTopActiveRegularUsers()
    {
        List<UserResponse> topActiveUsers = userStatisticsService.getTopActiveRegularUsers();
        return ResponseEntity.ok(topActiveUsers);
    }

    @CountRequests
    @GetMapping("/commit/{commitId}")
    public ResponseEntity<ScanResponse> getScanInfoByCommitId(
            @PathVariable("commitId") Long commitId
    ) {
        ScanResponse scanResponse = scanService.getScanInfoByCommitId(commitId);
        return ResponseEntity.ok(scanResponse);
    }
}
