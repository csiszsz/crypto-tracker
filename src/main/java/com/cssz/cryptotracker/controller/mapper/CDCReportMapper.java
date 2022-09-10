package com.cssz.cryptotracker.controller.mapper;

import com.cssz.cryptotracker.controller.model.ReportResponse;
import com.cssz.cryptotracker.repository.model.CDCReport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CDCReportMapper {

    ReportResponse cdcReportToReportResponse(CDCReport cdcReports);
    List<ReportResponse> cdcReportListToReportResponseList(List<CDCReport> cdcReports);
}
