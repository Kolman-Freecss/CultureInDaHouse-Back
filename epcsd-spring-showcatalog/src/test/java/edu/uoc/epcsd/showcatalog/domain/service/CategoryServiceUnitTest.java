package edu.uoc.epcsd.showcatalog.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de servicio CategoryService.
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryServiceUnitTest {

	/** Nombre de la categoria "Music shows". */
	private static final String MUSIC_SHOWS = "Music shows";

	/** Mock del repositorio de categoria. */
	@MockBean
	private CategoryRepository categoryRepository;

	/** Mock del servicio de catalogo. */
	@Autowired
	private CategoryService categoryService;

	/**
	 * Test que obtiene todas las categorías.
	 */
	@Test
	void should_find_all_categories() {
		log.debug("Test: should_find_all_categories()");
		final Category category1 = Category.builder().name("Category 1").build();
		final Category category2 = Category.builder().name("Category 2").build();
		final Category category3 = Category.builder().name("Category 3").build();

		final List<Category> categories = Arrays.asList(category1, category2, category3);

		Mockito.when(categoryRepository.findAllCategories()).thenReturn(categories);

		final List<Category> categoriesFromDb = categoryService.findAllCategories();
		assertThat(categoriesFromDb.get(0).getName()).isEqualToIgnoringCase("Category 1");
		assertThat(categoriesFromDb.get(1).getName()).isEqualToIgnoringCase("Category 2");
		assertThat(categoriesFromDb.get(2).getName()).isEqualToIgnoringCase("Category 3");
	}

	/**
	 * Test que obtiene la categoria por id.
	 */
	@Test
	void should_find_category_by_id() {
		log.debug("Test: should_find_category_by_id()");
		Long categoryId = 123L;
		final Category category = Category.builder().id(categoryId).build();
		Mockito.when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.ofNullable(category));

		final Optional<Category> categoryFromDb = categoryService.findCategoryById(categoryId);

		assertThat(categoryFromDb.get().getId()).isEqualTo(categoryId);
	}

	/**
	 * Test que obtiene la categoria por id y no existe.
	 */
	@Test
	void should_find_category_by_id_not_found() {
		log.debug("Test: should_find_category_by_id_not_found()");
		Long categoryId = 123L;

		Mockito.when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.empty());

		final Optional<Category> categoryFromDb = categoryService.findCategoryById(categoryId);

		assertThat(categoryFromDb).isEqualTo(Optional.empty());
	}

	/**
	* Test que obtiene la categoria por nombre.
	 */
	@Test
	void should_find_category_like_name() {
		log.debug("Test: should_find_category_like_name()");
		final Category categoryMusicShow = Category.builder().name(MUSIC_SHOWS).build();
		final List<Category> categories = Arrays.asList(categoryMusicShow);
		Mockito.when(categoryRepository.findCategoryLikeName(MUSIC_SHOWS)).thenReturn(categories);
		final List<Category> categoriesFromDb = categoryService.findCategoryLikeName(MUSIC_SHOWS);
		assertThat(categoriesFromDb.get(0).getName()).isEqualToIgnoringCase(MUSIC_SHOWS);
	}

	/**
	 * Test que crea una categoría
	 */
	@Test
	void should_create_category() {
		log.debug("Test: should_create_category()");

		Long categoryId = 10L;
		final Category category = Category.builder().name("Category 1").build();
		Mockito.when(categoryRepository.createCategory(category)).thenReturn(categoryId);
		categoryId = categoryService.createCategory(category);
		category.setId(categoryId);
		assertThat(category.getId()).isEqualTo(categoryId);
	}
	
	/**
	 * Test que actualiza una categoria.
	 */
	@Test
	void update_category() {
		log.debug("Test: update_category()");
		
		final Long categoryId = 75L;
		final Category categoryMusicShow = Category.builder().id(categoryId).name(MUSIC_SHOWS).description(MUSIC_SHOWS).build();
		Mockito.when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.of(categoryMusicShow));

		boolean success = categoryService.updateCategory(categoryMusicShow);
		assertThat(success).isTrue();
	}
}
