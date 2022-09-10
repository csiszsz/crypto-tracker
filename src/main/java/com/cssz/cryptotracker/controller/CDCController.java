package com.cssz.cryptotracker.controller;

import com.cssz.cryptotracker.controller.model.FileUploadResponse;
import com.cssz.cryptotracker.repository.model.CDCTransaction;
import com.cssz.cryptotracker.service.CDCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("/cdc")
public class CDCController {

    private final CDCService cdcService;

    @Autowired
    public CDCController(CDCService cdcService) {
        this.cdcService = cdcService;
    }

    @PostMapping("/import")
    public ResponseEntity<FileUploadResponse> importReport(@RequestParam("file") MultipartFile multipartFile) {

        String originalFilename = multipartFile.getOriginalFilename();

        List<CDCTransaction> cdcTransactions = cdcService.persistReport(multipartFile);

        return ResponseEntity.ok(FileUploadResponse.builder()
                .fileName(originalFilename)
                .nrOfRecords(cdcTransactions.size())
                .transactions(cdcTransactions)
                .build());
    }

}
