package edu.uoc.epcsd.showcatalog.domain.service;

import edu.uoc.epcsd.showcatalog.domain.*;
import edu.uoc.epcsd.showcatalog.domain.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Clase de test de servicio CompanyService.
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyServiceUnitTest {
	/** Nombre de la empresa "Teatro Apollo". */
	private static final String COMPANY_NAME = "Teatro Apollo";
	
	/** Mock del repositorio de empresa. */
	@MockBean
	private CompanyRepository companyRepository;

	/** Mock del servicio de catalogo. */
	@Autowired
	private CompanyService companyService;
	
	/**
	 * Test que obtiene la empresa por nombre.
	 */
	@Test
	void should_find_company_like_name() {
		log.debug("Test: should_find_company_like_name()");
		final Company categoryMusicShow = Company.builder().name(COMPANY_NAME).build();
		final List<Company> companies = Arrays.asList(categoryMusicShow);
		Mockito.when(companyRepository.findCompanyLikeName(COMPANY_NAME)).thenReturn(companies);
		final List<Company> companiesFromDb = companyService.findCompanyLikeName(COMPANY_NAME);
		assertThat(companiesFromDb.get(0).getName()).isEqualToIgnoringCase(COMPANY_NAME);
	}
	
	/**
	 * Test que crea una empresa.
	 */
	@Test
	void create_company() {
		log.debug("Test: create_company()");
		
		Long companyId = 10L;
		final Company companyTeatroApolo = Company.builder().name(COMPANY_NAME).build();
		Mockito.when(companyRepository.createCompany(companyTeatroApolo)).thenReturn(companyId);
		companyId = companyService.createCompany(companyTeatroApolo);
		companyTeatroApolo.setId(companyId);
		assertThat(companyTeatroApolo.getId()).isEqualTo(companyId);
	}
}
