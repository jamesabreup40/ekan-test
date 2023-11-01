package br.com.ekan.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private DocumentType documentType;
    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "beneficiaryId")
    private Beneficiary beneficiary;
    @JsonIgnore
    private LocalDate inclusionDate;
    @JsonIgnore
    private LocalDate updateDate;
}