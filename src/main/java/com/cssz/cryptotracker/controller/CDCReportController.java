package com.cssz.cryptotracker.controller;

import com.cssz.cryptotracker.controller.mapper.CDCReportMapper;
import com.cssz.cryptotracker.controller.model.ReportImportResponse;
import com.cssz.cryptotracker.controller.model.ReportListResponse;
import com.cssz.cryptotracker.controller.model.ReportResponse;
import com.cssz.cryptotracker.repository.model.CDCReport;
import com.cssz.cryptotracker.service.CDCReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("/cdc")
@Slf4j
public class CDCReportController {

    private final CDCReportService cdcService;
    private final CDCReportMapper cdcReportMapper;

    @Autowired
    public CDCReportController(CDCReportService cdcService, CDCReportMapper cdcReportMapper) {
        this.cdcService = cdcService;
        this.cdcReportMapper = cdcReportMapper;
    }

    @PostMapping("/")
    public ResponseEntity<ReportImportResponse> importReport(@RequestParam("file") MultipartFile multipartFile) {
        log.info("Persist report with filename: {}", multipartFile.getOriginalFilename());

        CDCReport cdcReport = cdcService.persistReport(multipartFile);

        return ResponseEntity.ok(ReportImportResponse.builder()
                .fileName(cdcReport.getReportFilename())
                .nrOfRecords(cdcReport.getTransactions().size())
                .transactions(cdcReport.getTransactions())
                .build());
    }

    @GetMapping("/")
    public ResponseEntity<ReportListResponse> listReports() {
        List<CDCReport> cdcReports = cdcService.listReports();
        List<ReportResponse> reportResponses = cdcReportMapper.cdcReportListToReportResponseList(cdcReports);

        ReportListResponse listResponse = ReportListResponse.builder().reports(reportResponses).build();

        return ResponseEntity.ok(listResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReport(@PathVariable Long id) {
        log.info("Delete report with id: {}", id);

        cdcService.deleteReport(id);

        return ResponseEntity.noContent().build();
    }

}
