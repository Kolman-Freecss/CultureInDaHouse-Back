package edu.uoc.epcsd.showcatalog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.CollectionUtils;

import edu.uoc.epcsd.showcatalog.config.TestConfig;
import edu.uoc.epcsd.showcatalog.domain.Company;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de company repository.
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyRepositoryIntegrationTest {

	/** Nombre de la empresa. */
	private static final String COMPANY_NAME = "Teatro Apolo";

	/** Repositorio de empresa. */
	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * Test que busca una empresa por su nombre.
	 */
	@Test
	void find_company_like_name() {
		log.info("Test: find_company_like_name()");
		final List<Company> companies = companyRepository.findCompanyLikeName(COMPANY_NAME);
		if(CollectionUtils.isEmpty(companies)) {
			log.error("No existen empresas con nombre: " + COMPANY_NAME);
		} else {
			final Company company = companies.get(0);
			log.info("Test: Empresa obtenida. Identificador: " + company.getId() + ". Nombre: " + company.getName());

			assertThat(company.getName()).isEqualTo(COMPANY_NAME);
		}
	}

	/**
	 * Test que guarda una empresa.
	 */
	@Test
	void add_company() {
		log.info("Test: add_company()");

		final Company company = Company.builder().name(UUID.randomUUID().toString()).address(UUID.randomUUID().toString()).email(UUID.randomUUID().toString()).mobileNumber("666666666").build();
		final Long companyId = companyRepository.createCompany(company);

		assertThat(companyId).isNotNull();
	}
}
