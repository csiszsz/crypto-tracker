package com.cssz.cryptotracker.repository.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "cdc_transaction")
public class CDCTransaction {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime timestamp;
    private String description;
    private String currency;
    private BigDecimal amount;
    private String toCurrency;
    private BigDecimal toAmount;
    private String nativeCurrency;
    private BigDecimal nativeAmount;
    private BigDecimal nativeAmountUsd;
    private String transactionKind;
    private String transactionHash;

}
