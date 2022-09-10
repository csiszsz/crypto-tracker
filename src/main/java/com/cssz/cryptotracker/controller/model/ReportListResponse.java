package com.cssz.cryptotracker.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportListResponse {

    private List<ReportResponse> reports;
}