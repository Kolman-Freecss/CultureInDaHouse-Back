package edu.uoc.epcsd.showcatalog.application.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uoc.epcsd.showcatalog.application.request.CreateCategoryRequest;
import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.service.CategoryService;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCategoryRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCompanyRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataShowRepository;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de controller CategoryRESTController.
 *
 * @author Grupo Slack Chronicles
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CategoryRESTController.class)
public class CategoryControllerUnitTest {
	/** Nombre de la categoria "Music shows". */
	private static final String MUSIC_SHOWS = "Music shows";

	/** Nombre de la categoria "Basket shows". */
	private static final String BASKET_SHOWS = "Basket shows";

	/** Nombre de la categoria "Circo". */
	private static final String CIRCO_SHOWS = "Circo";

	/** Path de la llamada api rest a categorias. */
	private static final String REST_CATEGORIES_PATH = "/api/categories";

	/** Path de la llamada api rest a categorias por nombre. */
	private static final String REST_CATEGORIES_NAME_PATH = "/api/categories/name";

	/** Mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** Mock del servicio de category. */
	@MockBean
	private CategoryService categoryService;

	/** Mock del repositorio spring para acto. */
	@MockBean
	private SpringDataShowRepository springDataShowRepository;

	/** Mock del repositorio spring para categoria. */
	@MockBean
	private SpringDataCategoryRepository springDataCategoryRepository;

	/** Mock del repositorio spring para empresa. */
	@MockBean
	private SpringDataCompanyRepository springDataCompanyRepository;

	/**
	 * Test que obtiene todas las categorias.
	 */
	@Test
	void find_categories_should_return_all() throws Exception {
		log.info("Test: find_categories_should_return_all()");
		final Category categoryOne = Category.builder().name(MUSIC_SHOWS).build();
		final Category categoryTwo = Category.builder().name(BASKET_SHOWS).build();
		final List<Category> categories = Arrays.asList(categoryOne, categoryTwo);
		Mockito.when(categoryService.findAllCategories()).thenReturn(categories);
		mockMvc.perform(get(REST_CATEGORIES_PATH).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is(MUSIC_SHOWS))).andExpect(jsonPath("$[1].name", is(BASKET_SHOWS)));
	}

	/**
	 * Test que obtiene las categorias por nombre.
	 */
	@Test
	void find_categories_like_name() throws Exception {
		log.info("Test: find_categories_like_name()");
		final Category category = Category.builder().name(CIRCO_SHOWS).build();
		final List<Category> categories = Arrays.asList(category);
		Mockito.when(categoryService.findCategoryLikeName(CIRCO_SHOWS)).thenReturn(categories);
		mockMvc.perform(get(REST_CATEGORIES_NAME_PATH).contentType(MediaType.APPLICATION_JSON).param("name", CIRCO_SHOWS)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is(CIRCO_SHOWS)));
	}

	/**
	 * Test que obtiene la categoria por id.
	 */
	@Test
	void find_category_by_id() throws Exception {
		log.info("Test: find_category_by_id()");
		final Long categoryId = 1L;
		final Category category = Category.builder().id(categoryId).name(MUSIC_SHOWS).build();
		Mockito.when(categoryService.findCategoryById(categoryId)).thenReturn(Optional.of(category));
		mockMvc.perform(get(REST_CATEGORIES_PATH + "/" + categoryId).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(MUSIC_SHOWS)));
	}
	
	/**
	 * Test que crea una categoria.
	 */
	@Test
	void add_category() throws Exception {
		log.info("Test: add_category()");
		final Long categoryId = 30L;
		final Category category = Category.builder().name(MUSIC_SHOWS).description(MUSIC_SHOWS).build();
		Mockito.when(categoryService.createCategory(category)).thenReturn(categoryId);

		final CreateCategoryRequest categoryRequest = new CreateCategoryRequest(category);
		final ObjectMapper mapper = new ObjectMapper();
		String companyJson = mapper.writeValueAsString(categoryRequest);

		mockMvc.perform(post(REST_CATEGORIES_PATH).contentType(MediaType.APPLICATION_JSON).content(companyJson)).andDo(print()).andExpect(status().isCreated());
	}
	
	/**
	 * Test que elimina una categoria.
	 */
	@Test
	void delete_category() throws Exception {
		log.info("Test: delete_category()");
		final Long categoryId = 35L;

		mockMvc.perform(delete(REST_CATEGORIES_PATH + "/" + categoryId).contentType(MediaType.APPLICATION_JSON).content("")).andDo(print()).andExpect(status().isOk());
	}
}
