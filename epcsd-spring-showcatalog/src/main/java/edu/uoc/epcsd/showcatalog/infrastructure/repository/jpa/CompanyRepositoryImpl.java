package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uoc.epcsd.showcatalog.domain.Company;
import edu.uoc.epcsd.showcatalog.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyRepositoryImpl implements CompanyRepository {

	private final SpringDataCompanyRepository jpaRepository;
	
	@Override
	public List<Company> findCompanyLikeName(String name) {
		return jpaRepository.findCompanyByNameContainingIgnoreCase(name).stream().map(CompanyEntity::toDomain).collect(Collectors.toList());
	}

	@Override
	public Long createCompany(Company company) {
		return jpaRepository.save(CompanyEntity.fromDomain(company)).getId();
	}
}
