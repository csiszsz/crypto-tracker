package com.cssz.cryptotracker.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportResponse {

    private Long id;

    private LocalDateTime reportAt;
    private String reportFilename;
    private LocalDateTime importedAt;
}