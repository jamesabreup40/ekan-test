package br.com.ekan.test.infra.resource;

import br.com.ekan.test.domain.Beneficiary;
import br.com.ekan.test.domain.Document;
import br.com.ekan.test.infra.repository.BeneficiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;
import static java.net.URI.create;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("beneficiary")
public class BeneficiaryResource {
    private final BeneficiaryRepository beneficiaryRepository;

    @ResponseStatus(code = OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Beneficiary>> all() {
        return ok(stream(beneficiaryRepository.findAll().spliterator(), false).collect(toSet()));
    }

    @ResponseStatus(code = OK)
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Beneficiary> oneById(@PathVariable UUID id) {
        return beneficiaryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(noContent().build());
    }

    @ResponseStatus(code = OK)
    @GetMapping(path = "/{id}/documents", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Document>> documentsById(@PathVariable UUID id) {
        return beneficiaryRepository.findById(id)
                .map(beneficiary -> ok(beneficiary.getDocuments()))
                .orElse(noContent().build());
    }

    @ResponseStatus(code = CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Beneficiary> createOne(@RequestBody Beneficiary incomingBeneficiary) {
        var inclusionDate = now();
        incomingBeneficiary.setInclusionDate(inclusionDate);
        incomingBeneficiary.getDocuments()
                .forEach(document -> {
                    document.setInclusionDate(inclusionDate);
                    document.setBeneficiary(incomingBeneficiary);
                });

        return created(
            create(
                format("http://localhost:8080/beneficiary/%s", beneficiaryRepository.save(incomingBeneficiary).getId())
            )
        ).build();
    }

    @ResponseStatus(code = OK)
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Beneficiary> updateOne(@PathVariable UUID id, @RequestBody Beneficiary incomingBeneficiary) {
        return beneficiaryRepository.findById(id)
                .map(foundBeneficiaryForUpdate -> {
                    copyProperties(incomingBeneficiary,
                            foundBeneficiaryForUpdate,
                            "id", "documents", "inclusionDate"
                    );
                    foundBeneficiaryForUpdate.setUpdateDate(now());
                    return foundBeneficiaryForUpdate;
                })
                .map(beneficiaryRepository::save)
                .map(ResponseEntity::ok)
                .orElse(noContent().build());
    }

    @ResponseStatus(code = OK)
    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public String deleteOne(@PathVariable UUID id) {
        beneficiaryRepository.deleteById(id);
        return "Beneficiary successfully excluded!";
    }
}


