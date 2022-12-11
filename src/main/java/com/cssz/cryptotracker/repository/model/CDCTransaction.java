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
    @Column(precision = 19, scale = 4)
    private BigDecimal amount;
    private String toCurrency;
    @Column(precision = 19, scale = 4)
    private BigDecimal toAmount;
    private String nativeCurrency;
    @Column(precision = 19, scale = 4)
    private BigDecimal nativeAmount;
    @Column(precision = 19, scale = 4)
    private BigDecimal nativeAmountUsd;
    private String transactionKind;
    private String transactionHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CDCReport report;

}
