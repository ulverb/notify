package com.mend.scan.repositories;

import com.mend.scan.models.IssueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<IssueModel, Long> {
}
