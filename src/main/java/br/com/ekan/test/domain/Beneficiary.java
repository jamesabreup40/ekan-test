package br.com.ekan.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    private String name;
    private String phone;
    private LocalDate birthDate;
    @OneToMany(fetch = EAGER, mappedBy = "beneficiary", cascade = ALL, orphanRemoval = true)
    private Set<Document> documents;
    @JsonIgnore
    private LocalDate inclusionDate;
    @JsonIgnore
    private LocalDate updateDate;
}