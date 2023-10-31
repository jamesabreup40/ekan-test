package br.com.ekan.test.infra.repository;

import br.com.ekan.test.domain.Beneficiary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeneficiaryRepository extends CrudRepository<Beneficiary, UUID> { }