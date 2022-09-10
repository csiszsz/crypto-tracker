package com.cssz.cryptotracker.repository.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cdc_report")
@Slf4j

public class CDCReport {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime reportAt;
    @Column(unique = true)
    private String reportFilename;

    @CreationTimestamp
    private LocalDateTime importedAt;

    @OneToMany(
            mappedBy = "report",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<CDCTransaction> transactions;

}
