package com.cssz.cryptotracker.controller.model;

import com.cssz.cryptotracker.repository.model.CDCTransaction;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FileUploadResponse {
    private String fileName;
    private int nrOfRecords;
    private List<CDCTransaction> transactions;
}
