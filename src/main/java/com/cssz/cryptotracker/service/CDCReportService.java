package com.cssz.cryptotracker.service;

import com.cssz.cryptotracker.repository.CDCReportRepository;
import com.cssz.cryptotracker.repository.model.CDCReport;
import com.cssz.cryptotracker.repository.model.CDCTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cssz.cryptotracker.service.model.TransactionFields.*;


@Service
@Transactional
public class CDCReportService {

    private final CDCReportRepository cdcReportRepository;

    private static final DateTimeFormatter transactionTimestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter reportTimestampFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @Autowired
    public CDCReportService(CDCReportRepository cdcReportRepository) {
        this.cdcReportRepository = cdcReportRepository;
    }

    public CDCReport persistReport(MultipartFile multipartFile) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            String originalFilename = multipartFile.getOriginalFilename();
            CDCReport report = createReport(originalFilename);

            Set<CDCTransaction> cdcTransactions = createTransactions(report, csvParser);
            report.setTransactions(cdcTransactions);

            return cdcReportRepository.saveAndFlush(report);

        } catch (IOException ioEx) {
            throw new RuntimeException("Error while parsing csv file ", ioEx);
        } catch (Exception ex) {
            if (ExceptionUtils.getThrowableList(ex).stream().anyMatch(throwable -> throwable instanceof ConstraintViolationException)) {
                throw new RuntimeException("Duplicated report");
            } else {
                throw new RuntimeException("Error while persisting report", ex);
            }
        }
    }

    public List<CDCReport> listReports() {
        return cdcReportRepository.findAll();
    }

    public void deleteReport(Long id) {
        cdcReportRepository.deleteById(id);
    }

    private CDCReport createReport(String originalFilename) {
        if (originalFilename != null) {
            String date = originalFilename.substring("crypto_transactions_record_".length(), originalFilename.indexOf(".csv"));


            return CDCReport.builder()
                    .reportFilename(originalFilename)
                    .reportAt(LocalDateTime.parse(date, reportTimestampFormatter))
                    .build();
        } else {
            throw new RuntimeException("Filename is null");
        }
    }

    private Set<CDCTransaction> createTransactions(CDCReport report, CSVParser csvParser) throws IOException {
        return csvParser.getRecords().stream()
                .map(csvRecord -> {
                    CDCTransaction cdcTransaction = parseTransaction(csvRecord);
                    cdcTransaction.setReport(report);
                    return cdcTransaction;
                })
                .collect(Collectors.toSet());
    }

    private static CDCTransaction parseTransaction(CSVRecord csvRecord) {
        return CDCTransaction.builder()
                .timestamp(LocalDateTime.parse(csvRecord.get(TIMESTAMP.getHeaderName()), transactionTimestampFormatter))
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
