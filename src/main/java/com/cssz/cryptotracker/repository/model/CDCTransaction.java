package com.cssz.cryptotracker.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CDCReport report;

}
