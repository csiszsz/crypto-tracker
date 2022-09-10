package com.cssz.cryptotracker.repository;

import com.cssz.cryptotracker.repository.model.CDCTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CDCTransactionRepository extends JpaRepository<CDCTransaction, Long> {
}
