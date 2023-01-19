package edu.uoc.epcsd.showcatalog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import edu.uoc.epcsd.showcatalog.domain.Show;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import edu.uoc.epcsd.showcatalog.config.TestConfig;
import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.CategoryEntity;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de repository.
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestConfig.class)
public class CatalogRepositoryIntegrationTest {

	/** Entity manager de test. */
	@Autowired
	private TestEntityManager entityManager;

	/** Repositorio de categoria. */
	@Autowired
	private CategoryRepository categoryRepository;

	/** Repositorio de shows. */
	@Autowired
	private ShowRepository showRepository;

	/**
	 * Test que guarda una categoria y la busca una vez persistida.
	 */
	@Test
	void whenFindByName_thenReturnCategory() {
		log.info("Test: whenFindByName_thenReturnCategory()");
		final String categoryName = "Music Shows";
		final Category category = Category.builder().name(categoryName).build();
		final CategoryEntity categoryEntity = CategoryEntity.fromDomain(category);
		entityManager.persistAndFlush(categoryEntity);
		log.info("Test: Categoria persistida. Identificador de la categoria: " + category.getId());

		final Category fromDb = categoryRepository.findCategoryById(categoryEntity.getId()).get();
		assertThat(fromDb.getName()).isEqualTo(category.getName());
	}
	
	/**
	 * Test que obtiene la categoria por el nombre.
	 */
	@Test
	void whenFindLikeName_thenReturnCategory() {
		log.info("Test: whenFindByLikeName_thenReturnCategory()");

		final List<Category> categoriesFromDb = categoryRepository.findCategoryLikeName("circo");
		assertThat(categoriesFromDb.get(0).getName()).isEqualToIgnoringCase("circo");
	}

	/**
	 * Test que obtiene los shows por el nombre.
	 */
	@Test
	void whenFindLikeName_thenReturnShow() {
		log.info("Test: whenFindLikeName_thenReturnShow()");

		final List<Show> showsFromDb = showRepository.findShowLikeName("Macbeth");
		assertThat(showsFromDb.get(0).getName()).isEqualToIgnoringCase("Macbeth");
	}

	/**
	 * Test que obtiene los shows por la categoria.
	 */
	@Test
	void whenFindLikeCategory_thenReturnShows() {
		log.info("Test: whenFindLikeCategory_thenReturnShows()");

		final List<Show> showsFromDb = showRepository.findShowLikeCategory("circo");
		assertThat(showsFromDb.get(0).getCategory().getName()).isEqualToIgnoringCase("circo");
	}
}
