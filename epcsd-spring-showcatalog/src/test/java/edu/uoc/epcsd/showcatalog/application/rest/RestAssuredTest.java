package edu.uoc.epcsd.showcatalog.application.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uoc.epcsd.showcatalog.application.request.CreateCategoryRequest;
import edu.uoc.epcsd.showcatalog.application.request.UpdateCategoryRequest;
import edu.uoc.epcsd.showcatalog.domain.Category;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test haciendo uso de RestAssured.
 *
 * @author Grupo Slack Chronicles
 */
@Log4j2
@TestMethodOrder(OrderAnnotation.class)
public class RestAssuredTest {
	/** Constante con la URL de los servicios REST del microservicio showcatalog. */
	private static final String URL_REST_SHOW_CATALOG = "http://95.216.107.92:18081/api";

	/** Nombre de la categoria 'Teatro'. */
	private static final String NOMBRE_CATEGORIA_TEATRO = "Teatro";

	/** Nombre de la categoria 'Test'. */
	private static final String NOMBRE_CATEGORIA_TEST = "Test";

	/** Descripcion de la categoria 'Teatro'. */
	private static final String DESCRIPCION_CATEGORIA_TEATRO = "Obras de teatro, musicales, drama, comedia...";

	/**
	 * Configuración de URL base.
	 */
	@BeforeAll
	public static void beforeAll() {
		RestAssured.baseURI = URL_REST_SHOW_CATALOG;
		log.debug("Inicializado RestAssured.baseURI = " + URL_REST_SHOW_CATALOG);
	}

	/**
	 * Test que obtiene las categorias a partir del nombre.
	 */
	@Test
	@Order(1)
	public void getCategoriesByNameTest() {
		log.info("getCategoriesByNameTest");
		// se obtiene la categoria por nombre y se verifica el json de respuesta
		given().log().all().param("name", NOMBRE_CATEGORIA_TEATRO).get("/categories/name").then().log().all().statusCode(HttpStatus.OK.value()).body("get(0).name", equalTo(NOMBRE_CATEGORIA_TEATRO))
				.and().body("get(0).description", equalTo(DESCRIPCION_CATEGORIA_TEATRO));
	}

	/**
	 * Test que crea una categoria de test.
	 */
	@Test
	@Order(2)
	public void postCategoryTest() {
		log.info("postCategoryTest");
		final Category category = Category.builder().name(NOMBRE_CATEGORIA_TEST).description(NOMBRE_CATEGORIA_TEST).build();

		// se crea el objeto json de la categoria
		final CreateCategoryRequest categoryRequest = new CreateCategoryRequest(category);
		final ObjectMapper mapper = new ObjectMapper();
		String categoryJson = null;
		try {
			categoryJson = mapper.writeValueAsString(categoryRequest);
		} catch (JsonProcessingException e) {
			log.error("Error al parsear la categoria en formato JSON");
		}

		// se realiza la llamada a crear categoria y se extrae el identificador de la categoria creada
		final String id = given().log().all().contentType("application/json; charset=utf-8").body(categoryJson).post("/categories").then().log().all().statusCode(HttpStatus.CREATED.value()).extract()
				.asString();
		log.info("Identificador de la categoria creada: " + id);
		assertNotNull(id);
	}

	/**
	 * Test que modifica la categoria de test.
	 */
	@Order(3)
	@Test
	public void putCategoryTest() {
		log.info("putCategoryTest");
		// se obtiene la categoria de test y se extraen sus datos
		final JsonPath jsonPath = given().param("name", NOMBRE_CATEGORIA_TEST).get("/categories/name").then().log().ifError().statusCode(HttpStatus.OK.value()).extract().jsonPath();
		final Integer id = (Integer) jsonPath.get("get(0).id");
		final String name = (String) jsonPath.get("get(0).name");
		final String description = (String) jsonPath.get("get(0).description");
		log.info("Identificador de la categoria a modificar: " + id);

		// se crea el objeto json de la categoria a modificar
		final UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest(Long.valueOf(id), name, description + " modificado");
		final ObjectMapper mapper = new ObjectMapper();
		String categoryJson = null;
		try {
			categoryJson = mapper.writeValueAsString(updateCategoryRequest);
		} catch (JsonProcessingException e) {
			log.error("Error al parsear la categoria en formato JSON");
		}

		// se realiza la llamada a modificar la categoria y se comprueba que la respuesta sea true
		final Boolean respuesta = given().log().all().contentType("application/json; charset=utf-8").body(categoryJson).put("/categories").then().log().all().statusCode(HttpStatus.OK.value())
				.extract().response().as(Boolean.class);
		assertTrue(respuesta);
	}

	/**
	 * Test que elimina la categoria de test.
	 */
	@Order(4)
	@Test
	public void deleteCategoryTest() {
		log.info("deleteCategoryTest");
		// se obtiene la categoria de test y se extrae el identificador
		final JsonPath jsonPath = given().param("name", NOMBRE_CATEGORIA_TEST).get("/categories/name").then().log().ifError().statusCode(HttpStatus.OK.value()).extract().jsonPath();
		final Integer id = (Integer) jsonPath.get("get(0).id");
		log.info("Identificador de la categoria a eliminar: " + id);

		// se realiza la llamada a eliminar categoria por id y se comprueba que la respuesta sea true
		final Boolean respuesta = given().log().all().pathParam("id", id).delete("/categories/{id}").then().log().all().statusCode(HttpStatus.OK.value()).extract().response().as(Boolean.class);
		assertTrue(respuesta);
	}
}
