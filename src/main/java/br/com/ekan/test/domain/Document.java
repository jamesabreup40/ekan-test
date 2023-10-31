package br.com.ekan.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    private DocumentType documentType;
    private String description;
    @JsonIgnore
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "beneficiaryId")
    private Beneficiary beneficiary;
    @JsonIgnore
    private LocalDate inclusionDate;
    @JsonIgnore
    private LocalDate updateDate;
}