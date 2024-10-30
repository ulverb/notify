package com.mend.scan.repositories;

import com.mend.scan.models.ScanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<ScanModel, Long> {
}
