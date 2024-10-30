package com.mend.dal.repositories;

import com.mend.dal.models.IssueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<IssueModel, Long>
{
    @Query(nativeQuery = true, value = """
        SELECT COUNT(i.id)
                FROM issues i
                WHERE i.scan_id IN (
                    SELECT MAX(s.id)
                    FROM scans s
                    GROUP BY s.commit_id
                )
    """)
    Long countIssuesWithoutDuplicatedScansFofAllUsers();


    @Query(nativeQuery = true, value = """
        SELECT COUNT(i.id)
                FROM users u
                JOIN scans s ON u.id = s.user_id
                JOIN issues i ON i.scan_id = s.id
                WHERE u.username = :username
                AND s.updated_at = (
                        SELECT MAX(updated_at)
                            FROM scans
                            WHERE commit_id = s.commit_id
                        )
    """)
    Long countIssuesWithoutDuplicatedScansByUsername(
        @Param("username") String username
    );

    @Query(nativeQuery = true, value = """
        SELECT
            COUNT(i.id)
            FROM users u
            JOIN scans s on u.user_id = s.user_id
            JOIN issues i ON i.scan_id = s.id
            WHERE u.username = :username
    """)
    Long countAllIssuesByUsername(
            @Param("username") String username
    );
}
