package edu.uoc.epcsd.showcatalog.application.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.uoc.epcsd.showcatalog.application.request.*;
import edu.uoc.epcsd.showcatalog.domain.*;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import edu.uoc.epcsd.showcatalog.domain.service.ShowService;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCategoryRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCompanyRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataShowRepository;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de controller ShowRESTController.
 *
 * @author Grupo Slack Chronicles
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ShowRESTController.class)
public class ShowControllerUnitTest {
	/** Nombre de la categoria "Circo". */
	private static final String CIRCO_SHOWS = "Circo";

	/** Nombre del acto "Cats". */
	private static final String CATS_SHOW = "Cats";

	/** Nombre del show "Cirque du soleil". */
	private static final String SHOW_NAME = "Cirque du soleil";

	/** Url de Google. */
	private static final String URL_GOOGLE = "http://www.google.es";

	/** Url de Yahoo. */
	private static final String URL_YAHOO = "http://www.yahoo.es";

	/** Path de la llamada api rest a shows por nombre. */
	private static final String REST_SHOW_NAME_PATH = "/api/shows/name";

	/** Path de la llamada api rest a categorias. */
	private static final String REST_SHOWS_PATH = "/api/shows";

	/** Path de la llamada api rest a shows por categoria. */
	private static final String REST_SHOWS_CATEGORY_PATH = "/api/shows/category";

	/** Path de la llamada api rest a actualizar estado de un acto. */
	private static final String REST_SHOW_UPDATE_STATUS = "/api/shows/status";

	/** Mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** Mock del servicio de catalogo. */
	@MockBean
	private ShowService showService;

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
	 * Test que obtiene los shows por nombre.
	 */
	@Test
	void find_shows_like_name() throws Exception {
		log.info("Test: find_shows_like_name()");
		final Show show = Show.builder().name(SHOW_NAME).build();
		final List<Show> shows = Arrays.asList(show);
		Mockito.when(showService.findShowLikeName(SHOW_NAME)).thenReturn(shows);
		mockMvc.perform(get(REST_SHOW_NAME_PATH).contentType(MediaType.APPLICATION_JSON).param("name", SHOW_NAME)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(SHOW_NAME)));
	}

	/**
	 * Test que obtiene los actos por nombre, categoria y fecha de actuacion.
	 */
	@Test
	void find_shows() throws Exception {
		log.info("Test: find_shows()");
		final Show show = Show.builder().name(CATS_SHOW).build();
		final List<Show> shows = Arrays.asList(show);
		Mockito.when(showService.findShows(CATS_SHOW, null, null, null)).thenReturn(shows);
		mockMvc.perform(get(REST_SHOWS_PATH).contentType(MediaType.APPLICATION_JSON).param("name", CATS_SHOW)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(CATS_SHOW)));
	}

	/**
	 * Test que obtiene los actos por categoria.
	 */
	@Test
	void find_shows_like_category() throws Exception {
		log.info("Test: find_shows_like_category()");
		final Category category = Category.builder().name(CIRCO_SHOWS).build();
		final Show show = Show.builder().category(category).build();
		final List<Show> shows = Arrays.asList(show);
		Mockito.when(showService.findShowLikeCategory(CIRCO_SHOWS)).thenReturn(shows);
		mockMvc.perform(get(REST_SHOWS_CATEGORY_PATH).contentType(MediaType.APPLICATION_JSON).param("name", CIRCO_SHOWS)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].category.name", is(CIRCO_SHOWS)));
	}

	/**
	 * Test que actualiza el estado de un acto.
	 */
	@Test
	void update_show_status() throws Exception {
		log.info("Test: update_show_status()");
		final Long showId = 44L;
		Mockito.when(showService.updateStateShow(showId, Status.OPEN)).thenReturn(true);

		final UpdateShowStatusRequest updateShowStatusRequest = new UpdateShowStatusRequest(Status.OPEN);
		final ObjectMapper mapper = new ObjectMapper();
		final String updateJson = mapper.writeValueAsString(updateShowStatusRequest);

		mockMvc.perform(put(REST_SHOW_UPDATE_STATUS + "/" + showId).contentType(MediaType.APPLICATION_JSON).content(updateJson)).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Test que obtiene la categoría a partir de un acto
	 */
	@Test
	void get_category_from_show() throws Exception {
		log.info("Test: get_category_from_show()");
		final Category category = Category.builder().build();
		final Long showId = 123L;
		Show.builder().id(showId).category(category).build();

		Mockito.when(showService.findCategoryByShowId(showId)).thenReturn(Optional.ofNullable(category));
		mockMvc.perform(get("/api/shows/" + showId + "/category").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(category.getName())));
	}

	/**
	 * Test que da not found para obtener la categoría a partir de un acto puesto que el acto no existe
	 */
	@Test
	void get_category_from_show_not_found() throws Exception {
		log.info("Test: get_category_from_show_not_found()");
		final Long showId = 123L;
		Mockito.when(showService.findCategoryByShowId(showId)).thenReturn(Optional.empty());
		mockMvc.perform(get(REST_SHOWS_PATH + showId + "/category").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotFound());
	}

	/**
	 * Test que obtiene los detalles de un acto.
	 */
	@Test
	void get_show_details() throws Exception {
		log.info("Test: get_show_details()");
		final Long showId = 11L;
		final Show show = Show.builder().name(SHOW_NAME).build();
		Mockito.when(showService.findShowById(showId)).thenReturn(Optional.of(show));
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + showId).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(SHOW_NAME)));
	}

	/**
	 * Test que obtiene todas las categorias.
	 */
	@Test
	void get_show_performances() throws Exception {
		log.info("Test: get_show_performances()");
		final Long showId = 22L;
		final Performance performanceOne = Performance.builder().date(LocalDate.now()).time(LocalTime.now()).streamingURL(URL_GOOGLE).remainingSeats(100L).build();
		final Performance performanceTwo = Performance.builder().date(LocalDate.now()).time(LocalTime.now()).streamingURL(URL_YAHOO).remainingSeats(150L).build();
		final Set<Performance> performances = new HashSet<>();
		performances.add(performanceOne);
		performances.add(performanceTwo);
		Mockito.when(showService.getShowPerformances(showId)).thenReturn(performances);
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + showId + "/performances").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	/**
	 * Test que crea un acto.
	 */
	@Test
	void add_show() throws Exception {
		log.info("Test: add_show()");
		final Long showId = 30L;
		final Show show = Show.builder().name(SHOW_NAME).build();
		Mockito.when(showService.createShow(show)).thenReturn(showId);

		final CreateShowRequest showRequest = new CreateShowRequest(show);
		final ObjectMapper mapper = new ObjectMapper();
		final String showJson = mapper.writeValueAsString(showRequest);

		mockMvc.perform(post(REST_SHOWS_PATH).contentType(MediaType.APPLICATION_JSON).content(showJson)).andDo(print()).andExpect(status().isCreated());
	}

	/**
	 * Test que actualiza un acto.
	 */
	@Test
	void update_show() throws Exception {
		log.info("Test: update_show()");
		final Long showId = 31L;
		final Long categoryId = 1L;
		final Category category = Category.builder().id(categoryId).build();
		final Show show = Show.builder().id(showId).name(SHOW_NAME).description(SHOW_NAME).image("").duration(120D).capacity(100L).onSaleDate(LocalDate.now()).category(category).build();
		Mockito.when(showService.updateShow(show)).thenReturn(true);

		final UpdateShowRequest updateShowRequest = new UpdateShowRequest(show.getId(), show.getName(), show.getDescription(), show.getImage(), null, show.getDuration(), show.getCapacity(),
				show.getOnSaleDate(), show.getCategory().getId());
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		final String showJson = mapper.writeValueAsString(updateShowRequest);

		mockMvc.perform(put(REST_SHOWS_PATH).contentType(MediaType.APPLICATION_JSON).content(showJson)).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Test que crea una actuacion.
	 */
	@Test
	void add_performance() throws Exception {
		log.info("Test: add_performance()");
		final Long showId = 31L;
		final Performance performance = Performance.builder().date(LocalDate.now()).time(LocalTime.now()).streamingURL(URL_GOOGLE).remainingSeats(100L).build();

		final CreatePerformanceRequest performanceRequest = new CreatePerformanceRequest(performance);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		final String performanceJson = mapper.writeValueAsString(performanceRequest);

		mockMvc.perform(put(REST_SHOWS_PATH + "/" + showId).contentType(MediaType.APPLICATION_JSON).content(performanceJson)).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Test que cancela un acto.
	 */
	@Test
	void cancel_show() throws Exception {
		log.info("Test: cancel_show()");
		final Long showId = 35L;

		mockMvc.perform(delete(REST_SHOWS_PATH + "/" + showId).contentType(MediaType.APPLICATION_JSON).content("")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Test que crea una actuacion.
	 */
	@Test
	void add_comment() throws Exception {
		log.info("Test: add_comment()");
		final Long showId = 31L;
		final ShowComment showComment = ShowComment.builder().comment("Comment").rating(3).build();

		final CreateShowCommentRequest showCommentRequest = new CreateShowCommentRequest(showComment);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		final String showCommentJson = mapper.writeValueAsString(showCommentRequest);

		mockMvc.perform(post(REST_SHOWS_PATH + "/" + showId + "/comment").contentType(MediaType.APPLICATION_JSON).content(showCommentJson)).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Test que crea una actuacion.
	 */
	@Test
	void add_comment_invalid_rating() throws Exception {
		log.info("Test: add_comment_invalid_rating()");
		final Long showId = 31L;
		final ShowComment showComment = ShowComment.builder().rating(7).comment("Comment").build();

		final CreateShowCommentRequest showCommentRequest = new CreateShowCommentRequest(showComment);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		final String showCommentJson = mapper.writeValueAsString(showCommentRequest);

		mockMvc.perform(post(REST_SHOWS_PATH + "/" + showId + "/comment").contentType(MediaType.APPLICATION_JSON).content(showCommentJson)).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Test que obtiene todas los comentarios de un show.
	 */
	@Test
	void get_show_comments() throws Exception {
		log.info("Test: get_show_comments()");
		final Long showId = 22L;
		final ShowComment commentOne = ShowComment.builder().comment("Hola uno").build();
		final ShowComment commentTwo = ShowComment.builder().comment("Hola dos").build();
		final Set<ShowComment> comments = new HashSet<>();
		comments.add(commentOne);
		comments.add(commentTwo);
		Mockito.when(showService.getShowComments(showId)).thenReturn(comments);
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + showId + "/comments").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	/**
	 * Test que obtiene el estado de un show.
	 */
	@Test
	void get_show_status() throws Exception {
		log.info("Test: get_show_status()");
		final Long showId = 22L;
		final Status status = Status.CREATED;
		Mockito.when(showService.getShowStatus(showId)).thenReturn(status);
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + showId + "/status").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", is(Status.CREATED.toString())));
	}

	/**
	 * Test que obtiene la fecha inicial de un show.
	 */
	@Test
	void get_show_start_date() throws Exception {
		log.info("Test: get_show_start_date()");
		final LocalDate startDate = LocalDate.parse("2023-01-01");
		final Performance performance1 = Performance.builder().date(LocalDate.parse("2023-01-01")).build();
		final Performance performance2 = Performance.builder().date(LocalDate.parse("2023-03-01")).build();
		final Long actualShowId = 140L;
		Set<Performance> actualShowPerformances = new HashSet<>();
		actualShowPerformances.add(performance1);
		actualShowPerformances.add(performance2);
		Mockito.when(showService.getShowStartDate(actualShowId)).thenReturn(startDate);
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + actualShowId + "/startDate").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", is(startDate.toString())));
	}

	/**
	 * Test que obtiene la fecha final de un show.
	 */
	@Test
	void get_show_final_date() throws Exception {
		log.info("Test: get_show_final_date()");
		final LocalDate finalDate = LocalDate.parse("2023-03-01");
		final Performance performance1 = Performance.builder().date(LocalDate.parse("2023-01-01")).build();
		final Performance performance2 = Performance.builder().date(LocalDate.parse("2023-03-01")).build();
		final Long actualShowId = 140L;
		Set<Performance> actualShowPerformances = new HashSet<>();
		actualShowPerformances.add(performance1);
		actualShowPerformances.add(performance2);
		Mockito.when(showService.getShowFinalDate(actualShowId)).thenReturn(finalDate);
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + actualShowId + "/finalDate").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", is(finalDate.toString())));
	}
	
	/**
	 * Test que obtiene el tiempo que queda para empezar un show.
	 */
	@Test
	void get_time_util_ready_show() throws Exception {
		log.info("Test: get_time_util_ready_show()");
		final LocalDate date = LocalDate.now();
		final Long actualShowId = 140L;
		Mockito.when(showService.getShowStartDate(actualShowId)).thenReturn(date);
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + actualShowId + "/getTimeUtilReadyShow").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	/**
	 * Test que obtiene el tiempo que queda para empezar un show de una fecha pasada.
	 */
	@Test
	void get_time_util_ready_show_past() throws Exception {
		log.info("Test: get_time_util_ready_show_past()");
		final LocalDate date = LocalDate.parse("2023-03-03");
		final Long actualShowId = 140L;
		Mockito.when(showService.getShowStartDate(actualShowId)).thenReturn(date);
		mockMvc.perform(get(REST_SHOWS_PATH + "/" + actualShowId + "/getTimeUtilReadyShow").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
}
