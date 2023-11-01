package br.com.ekan.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static java.util.UUID.randomUUID;

@Entity
@Getter
@Setter
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String phone;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "beneficiary", cascade = ALL, orphanRemoval = true)
    private Set<Document> documents;
    @JsonIgnore
    private LocalDate inclusionDate;
    @JsonIgnore
    private LocalDate updateDate;
}