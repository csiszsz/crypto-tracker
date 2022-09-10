package com.cssz.cryptotracker.repository;

import com.cssz.cryptotracker.repository.model.CDCReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CDCReportRepository extends JpaRepository<CDCReport, Long> {
}
