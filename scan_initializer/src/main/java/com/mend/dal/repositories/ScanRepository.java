package com.mend.dal.repositories;

import com.mend.dal.models.ScanModel;
import com.mend.dal.types.ScanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScanRepository extends JpaRepository<ScanModel, Long>
{

    Long countByScanStatus(ScanStatus scanStatus);

    List<ScanModel> findByUserId(Long userId);

    @Query(nativeQuery = true, value = """
        SELECT *
            FROM scans
            WHERE commit_id = :commitId
              AND status = 'COMPLETED'
            ORDER BY updated_at DESC
            LIMIT 1
    """)
    Optional<ScanModel> findLatestCompletedScanByCommitId(
            @Param("commitId") Long commitId
    );
}
