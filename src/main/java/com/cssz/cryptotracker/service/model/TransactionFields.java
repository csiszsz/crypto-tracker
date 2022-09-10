package com.cssz.cryptotracker.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionFields {

    TIMESTAMP("Timestamp (UTC)"),
    DESCRIPTION("Transaction Description"),
    CURRENCY("Currency"),
    AMOUNT("Amount"),
    TO_CURRENCY("To Currency"),
    TO_AMOUNT("To Amount"),
    NATIVE_CURRENCY("Native Currency"),
    NATIVE_AMOUNT("Native Amount"),
    NATIVE_AMOUNT_USD("Native Amount (in USD)"),
    TRANSACTION_KIND("Transaction Kind"),
    TRANSACTION_HASH("Transaction Hash");

    private String headerName;


}
