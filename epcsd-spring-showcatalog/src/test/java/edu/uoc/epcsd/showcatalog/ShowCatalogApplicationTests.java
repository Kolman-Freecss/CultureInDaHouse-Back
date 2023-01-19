package edu.uoc.epcsd.showcatalog;

import static org.assertj.core.api.Assertions.assertThat;

import edu.uoc.epcsd.showcatalog.domain.service.CategoryService;
import edu.uoc.epcsd.showcatalog.domain.service.CompanyService;
import edu.uoc.epcsd.showcatalog.domain.service.ShowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShowCatalogApplicationTests {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ShowService showService;

	@Test
	void contextLoads() {
		// Comprobación de que todos los servicios se encuentran en el contexto
		assertThat(categoryService).isNotNull();
		assertThat(companyService).isNotNull();
		assertThat(showService).isNotNull();
	}
}
