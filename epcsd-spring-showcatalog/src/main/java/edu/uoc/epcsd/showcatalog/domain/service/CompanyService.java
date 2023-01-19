package edu.uoc.epcsd.showcatalog.domain.service;

import edu.uoc.epcsd.showcatalog.domain.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findCompanyLikeName(String name);

    Long createCompany(Company company);
}
