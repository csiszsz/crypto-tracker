package com.cssz.cryptotracker.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private Long id;

    private LocalDateTime reportAt;
    private String reportFilename;
    private LocalDateTime importedAt;
}