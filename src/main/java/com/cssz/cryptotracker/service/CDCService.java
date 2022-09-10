package com.cssz.cryptotracker.service;

import com.cssz.cryptotracker.repository.CDCTransactionRepository;
import com.cssz.cryptotracker.repository.model.CDCTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.cssz.cryptotracker.service.model.TransactionFields.*;


@Service
public class CDCService {

    private final CDCTransactionRepository cdcTransactionRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CDCService(CDCTransactionRepository cdcTransactionRepository) {
        this.cdcTransactionRepository = cdcTransactionRepository;
    }

    public List<CDCTransaction> persistReport(MultipartFile multipartFile) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {

            List<CDCTransaction> cdcTransactions = csvParser.getRecords().stream()
                    .map(CDCService::parseTransaction)
                    .collect(Collectors.toList());

            return cdcTransactionRepository.saveAll(cdcTransactions);

        } catch (IOException e) {
            throw new RuntimeException("Error while parsing csv file: " + e.getMessage());
        }
    }

    private static CDCTransaction parseTransaction(CSVRecord csvRecord) {
        return CDCTransaction.builder()
                .timestamp(LocalDateTime.parse(csvRecord.get(TIMESTAMP.getHeaderName()), formatter))
                .description(csvRecord.get(DESCRIPTION.getHeaderName()))
                .currency(csvRecord.get(CURRENCY.getHeaderName()))
                .amount(bigDecimalFromString(csvRecord.get(AMOUNT.getHeaderName())))
                .toCurrency(csvRecord.get(TO_CURRENCY.getHeaderName()))
                .toAmount(bigDecimalFromString(csvRecord.get(TO_AMOUNT.getHeaderName())))
                .nativeCurrency(csvRecord.get(NATIVE_CURRENCY.getHeaderName()))
                .nativeAmount(bigDecimalFromString(csvRecord.get(NATIVE_AMOUNT.getHeaderName())))
                .nativeAmountUsd(bigDecimalFromString(csvRecord.get(NATIVE_AMOUNT_USD.getHeaderName())))
                .transactionKind(csvRecord.get(TRANSACTION_KIND.getHeaderName()))
                .transactionHash(csvRecord.get(TRANSACTION_HASH.getHeaderName()))
                .build();
    }

    private static BigDecimal bigDecimalFromString(String value) {
        return StringUtils.isNotEmpty(value) ? new BigDecimal(value) : null;
    }
}
