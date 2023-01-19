package edu.uoc.epcsd.showcatalog.domain.service;

import edu.uoc.epcsd.showcatalog.domain.*;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.showcatalog.domain.repository.ShowRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Clase de test de servicio ShowService.
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowServiceUnitTest {

	/** Nombre de la categoria "Music shows". */
	private static final String MUSIC_SHOWS = "Music shows";

	/** Nombre del show "Macbeth". */
	private static final String SHOW_NAME = "Macbeth";

	/** Mock del repositorio de categoria. */
	@MockBean
	private CategoryRepository categoryRepository;

	/** Mock del repositorio de acto. */
	@MockBean
	private ShowRepository showRepository;

	/** Mock del servicio de catalogo. */
	@Autowired
	private ShowService showService;

	/**
	 * Test de que existe el show.
	 */
	@Test
	void should_find_show_by_id() {
		log.debug("Test: should_find_show_by_id()");
		final Category categoryMusicShow = Category.builder().name(MUSIC_SHOWS).build();
		final Show actualShow = Show.builder().name("Kiss - The final tour").category(categoryMusicShow).capacity(45000L).build();
		final Long actualShowId = 120L;
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		final Show show = showService.findShowById(actualShowId).get();
		assertThat(show).usingRecursiveComparison().isEqualTo(actualShow);
	}

	/**
	 * Test de que no existe el show.
	 */	
	@Test
	void should_not_find_show_by_id() {
		log.debug("Test: should_not_find_show_by_id()");
		final Long notRealId = 120L;
		Mockito.when(showRepository.findShowById(notRealId)).thenReturn(null);
		final Optional<Show> show = showService.findShowById(notRealId);
		assertThat(show).isNull();
	}
	
	/**
	 * Test que busca actos por nombre, categoria y fecha de actuacion.
	 */
	@Test
	void find_shows() {
		log.debug("Test: find_shows()");
		List<Show> shows = null;
		Mockito.when(showRepository.findShows("Cats", 1L, LocalDate.now(), Status.CREATED)).thenReturn(shows);
		assertThat(shows).isNull();
	}

	/**
	 * Test que obtiene las actuaciones de un acto.
	 */
	@Test
	void get_show_performances() {
		log.debug("Test: get_show_performances()");
		final Category categoryMusicShow = Category.builder().name(MUSIC_SHOWS).build();
		final Show actualShow = Show.builder().name("U2 360 Tour").category(categoryMusicShow).capacity(60000L).build();
		Set<Performance> actualPerformances = new HashSet<>();
		actualPerformances.add(Performance.builder().remainingSeats(100L).build());
		actualShow.setPerformances(actualPerformances);
		final Long actualShowId = 140L;
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		final Set<Performance> performances = showService.getShowPerformances(actualShowId);
		assertThat(performances).usingRecursiveComparison().isEqualTo(actualPerformances);
	}
	
	/**
	 * Test que crea un acto.
	 */
	@Test
	void create_show() {
		log.debug("Test: create_show()");
		
		final Long categoryId = 25L;
		final Category categoryMusicShow = Category.builder().id(categoryId).name(MUSIC_SHOWS).build();
		Mockito.when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.of(categoryMusicShow));
		
		final Show actualShow = Show.builder().name("Korn Tour").category(categoryMusicShow).capacity(50000L).build();
		actualShow.setStatus(Status.CREATED);
		final Long actualShowId = 175L;
		Mockito.when(showRepository.createShow(actualShow)).thenReturn(actualShowId);
		actualShow.setId(actualShowId);
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		showService.createShow(actualShow);
		assertThat(actualShow.getId()).isEqualTo(actualShowId);
	}

	/**
	 * Test que actualiza un acto.
	 */
	@Test
	void update_show() {
		log.debug("Test: update_show()");
		
		final Long categoryId = 25L;
		final Category categoryMusicShow = Category.builder().id(categoryId).name(MUSIC_SHOWS).build();
		Mockito.when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.of(categoryMusicShow));
		
		final Long actualShowId = 175L;
		final Show actualShow = Show.builder().id(actualShowId).name(SHOW_NAME).category(categoryMusicShow).capacity(50000L).status(Status.CREATED).build();
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		boolean success = showService.updateShow(actualShow);
		assertThat(success).isTrue();
	}
	
	/**
	 * Test que intenta crear un acto pero no existe la categoria asociada al acto.
	 */
	@Test
	void should_not_find_category_create_show() {
		log.debug("Test: should_not_find_show_create_show()");
		
		final Long categoryId = 45L;
		final Category categoryMusicShow = Category.builder().id(categoryId).name(MUSIC_SHOWS).build();
		Mockito.when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.empty());
		
		final Show actualShow = Show.builder().name("Metallica Tour").category(categoryMusicShow).capacity(50000L).build();
		final Long showId = showService.createShow(actualShow);
		
		assertThat(showId).isNull();
	}
	
	/**
	 * Test que cancela un acto.
	 */
	@Test
	void cancel_show() {
		log.debug("Test: cancel_show()");
		final Category categoryMusicShow = Category.builder().name(MUSIC_SHOWS).build();
		final Show actualShow = Show.builder().name("Green Day Tour").category(categoryMusicShow).capacity(20000L).build();
		actualShow.setStatus(Status.CREATED);
		final Long actualShowId = 150L;
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		showService.cancelShow(actualShowId);
		assertThat(actualShow.getStatus()).isEqualTo(Status.CANCELLED);
	}
	
	/**
	 * Test que quiere cancelar un show que no existe.
	 */
	@Test
	void should_not_find_show_cancel_show() {
		log.debug("Test: should_not_find_show_cancel_show()");
		
		final Long showId = 75L;
		Mockito.when(showRepository.findShowById(showId)).thenReturn(Optional.empty());
		
		showService.cancelShow(showId);
		
		assertThat(showId).isNotNull();
	}

	/**
	 * Test que obtiene los shows por nombre.
	 */
	@Test
	void should_find_shows_like_name() {
		log.debug("Test: sshould_find_shows_like_name()");
		final Show show = Show.builder().name(SHOW_NAME).build();
		final List<Show> shows = Arrays.asList(show);
		Mockito.when(showRepository.findShowLikeName(SHOW_NAME)).thenReturn(shows);
		final List<Show> showsFromDb = showService.findShowLikeName(SHOW_NAME);
		assertThat(showsFromDb.get(0).getName()).isEqualToIgnoringCase(SHOW_NAME);
	}

	/**
	 * Test que obtiene los shows por categoria.
	 */
	@Test
	void should_find_show_like_category() {
		log.debug("Test: should_find_show_like_category()");
		final Category category = Category.builder().name(MUSIC_SHOWS).build();
		final Show show = Show.builder().category(category).build();
		final List<Show> shows = Arrays.asList(show);
		Mockito.when(showRepository.findShowLikeCategory(MUSIC_SHOWS)).thenReturn(shows);
		final List<Show> showsFromDb = showService.findShowLikeCategory(MUSIC_SHOWS);
		assertThat(showsFromDb.get(0).getCategory().getName()).isEqualToIgnoringCase(MUSIC_SHOWS);
	}

	/**
	 * Test para actualizar el estado del acto.
	 */
	@Test
	public void updateStateShowNoIdTest() {
		log.debug("Test: updateStateShowTestNoId()");
		final Long notRealId = 120L;
		Mockito.when(showRepository.findShowById(notRealId)).thenReturn(Optional.empty());
		final boolean update = showService.updateStateShow(notRealId, Status.OPEN);
		assertThat(update).isEqualTo(false);
	}

	/**
	 * Test para actualizar el estado del acto.
	 */
	@Test
	public void updateStateShowChangedTest1() {
		long id = 1;
		log.debug("Test: updateStateShowTestNoId()");
		final Show actualShow = Show.builder().name("Batman").capacity(100L).build();
		Mockito.when(showRepository.findShowById(id)).thenReturn(Optional.of(actualShow));
		final boolean update = showService.updateStateShow(id, Status.OPEN);
		assertThat(update).isEqualTo(true);
	}

	/**
	 * Test para actualizar el estado del acto.
	 */
	@Test
	public void updateStateShowChangedTest2() {
		long id = 1;
		log.debug("Test: updateStateShowTestNoId()");
		final Show actualShow = Show.builder().name("Batman").capacity(100L).build();
		Mockito.when(showRepository.findShowById(id)).thenReturn(Optional.of(actualShow));
		final boolean update = showService.updateStateShow(id, Status.OPEN);
		assertThat(update).isEqualTo(true);
	}

	/**
	 * Test de que existe la categoría porque el show existe.
	 */
	@Test
	void should_find_category_by_show_id() {
		log.debug("Test: should_find_category_by_show_id()");
		final Category categoryMusicShow = Category.builder().name(MUSIC_SHOWS).build();
		final Show actualShow = Show.builder().name("Kiss - The final tour").category(categoryMusicShow).capacity(45000L).build();
		final Long actualShowId = 120L;
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		final Category category = showService.findCategoryByShowId(actualShowId).get();
		assertThat(category).usingRecursiveComparison().isEqualTo(categoryMusicShow);
	}

	/**
	 * Test de que no existe la categoría porque el acto no existe.
	 */
	@Test
	void should_not_find_category_by_show_id() {
		log.debug("Test: should_not_find_category_by_show_id()");
		final Long notRealId = 120L;
		Mockito.when(showRepository.findShowById(notRealId)).thenReturn(Optional.empty());
		final Optional<Category> category = showService.findCategoryByShowId(notRealId);
		assertThat(category).isEqualTo(Optional.empty());
	}
	
	/**
	 * Test que añade un comentario a un acto que existe.
	 */
	@Test
	void add_comment_show_exist() {
		log.debug("Test: add_comment()");

		final Long actualShowId = 175L;
		final Show actualShow = Show.builder().id(actualShowId).name(SHOW_NAME).status(Status.CREATED).build();
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		
		final ShowComment showComment = ShowComment.builder().comment("esto es un comentario de test").build();
		boolean success = showService.addComment(actualShow.getId(), showComment);
		assertThat(success).isTrue();
	}

	/**
	 * Test que añade un comentario a un acto que no existe.
	 */
	@Test
	void add_comment_show_not_exist() {
		log.debug("Test: add_comment()");

		final Long actualShowId = 77L;
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.empty());

		final ShowComment showComment = ShowComment.builder().comment("esto es un comentario de test").build();
		boolean success = showService.addComment(actualShowId, showComment);
		assertThat(success).isFalse();
	}

	/**
	 * Test que obtiene los comentarios de un show
	 */
	@Test
	void get_show_comments() {
		log.debug("Test: get_show_comments()");
		final Category categoryMusicShow = Category.builder().name(MUSIC_SHOWS).build();
		final Show actualShow = Show.builder().name("U2 360 Tour").category(categoryMusicShow).capacity(60000L).build();
		Set<ShowComment> actualShowComments = new HashSet<>();
		actualShowComments.add(ShowComment.builder().build());
		actualShow.setComments(actualShowComments);
		final Long actualShowId = 140L;
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		final Set<ShowComment> comments = showService.getShowComments(actualShowId);
		assertThat(comments).usingRecursiveComparison().isEqualTo(actualShowComments);
	}

	/**
	 * Test que obtiene el estado de un show.
	 */
	@Test
	void get_show_status() {
		log.debug("Test: get_show_status()");
		final Status showStatus = Status.CREATED;
		final Show actualShow = Show.builder().name("U2 360 Tour").status(showStatus).build();
		final Long actualShowId = 140L;
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(actualShow));
		final Status status = showService.getShowStatus(actualShowId);
		assertThat(status).usingRecursiveComparison().isEqualTo(showStatus);
	}

	/**
	 * Test que obtiene la fecha inicial de un show.
	 */
	@Test
	void get_show_start_date() {
		log.debug("Test: get_show_start_date");
		final LocalDate startDate = LocalDate.parse("2023-01-01");
		final Performance performance1 = Performance.builder().date(LocalDate.parse("2023-01-01")).build();
		final Performance performance2 = Performance.builder().date(LocalDate.parse("2023-03-01")).build();
		final Long actualShowId = 140L;
		Set<Performance> actualShowPerformances = new HashSet<>();
		actualShowPerformances.add(performance1);
		actualShowPerformances.add(performance2);
		final Show show = Show.builder().performances(actualShowPerformances).build();
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(show));
		final LocalDate date = showService.getShowStartDate(actualShowId);
		assertThat(date).usingDefaultComparator().isEqualTo(startDate);
	}

	/**
	 * Test que obtiene la fecha final de un show.
	 */
	@Test
	void get_show_final_date() {
		log.debug("Test: get_show_final_date");
		final LocalDate finalDate = LocalDate.parse("2023-03-01");
		final Performance performance1 = Performance.builder().date(LocalDate.parse("2023-01-01")).build();
		final Performance performance2 = Performance.builder().date(LocalDate.parse("2023-03-01")).build();
		final Long actualShowId = 140L;
		Set<Performance> actualShowPerformances = new HashSet<>();
		actualShowPerformances.add(performance1);
		actualShowPerformances.add(performance2);
		final Show show = Show.builder().performances(actualShowPerformances).build();
		Mockito.when(showRepository.findShowById(actualShowId)).thenReturn(Optional.of(show));
		final LocalDate date = showService.getShowFinalDate(actualShowId);
		assertThat(date).usingDefaultComparator().isEqualTo(finalDate);
	}
}
