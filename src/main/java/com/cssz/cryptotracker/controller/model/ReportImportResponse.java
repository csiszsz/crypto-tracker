package com.cssz.cryptotracker.controller.model;

import com.cssz.cryptotracker.repository.model.CDCTransaction;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ReportImportResponse {
    private String fileName;
    private int nrOfRecords;
    private Set<CDCTransaction> transactions;
}
