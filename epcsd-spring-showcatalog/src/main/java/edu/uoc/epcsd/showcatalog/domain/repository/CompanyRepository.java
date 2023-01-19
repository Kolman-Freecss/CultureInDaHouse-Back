package edu.uoc.epcsd.showcatalog.domain.repository;

import java.util.List;

import edu.uoc.epcsd.showcatalog.domain.Company;

public interface CompanyRepository {

	List<Company> findCompanyLikeName(String name);

	Long createCompany(Company company);
}
