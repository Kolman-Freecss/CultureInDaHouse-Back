package edu.uoc.epcsd.showcatalog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import edu.uoc.epcsd.showcatalog.domain.ShowComment;
import edu.uoc.epcsd.showcatalog.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import edu.uoc.epcsd.showcatalog.config.TestConfig;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de show repository.
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestConfig.class)
public class ShowRepositoryIntegrationTest {
	
	/** Nombre del acto "Cats". */
	private static final String CATS_SHOW = "Cats";
	
	/** Identificador de la categoria de teatro. */
	private static final Long CATEGOY_ID_THEATER = 1L;
	
	/** Fecha de la actuacion. */
	private static final LocalDate DATE_PERFORMANCE = LocalDate.of(2022, 5, 15);
	
	/** Repositorio de acto. */
	@Autowired
	private ShowRepository showRepository;
	
	/**
	 * Test que guarda una actuacion y la busca una vez persistida.
	 */
	@Test
	void add_show_performance() {
		log.info("Test: add_show_performance()");
		final Long showId = 3L;
		final Optional<Show> optionalShow = showRepository.findShowById(3L);
		if(optionalShow.isPresent()) {
			final Show show =  optionalShow.get();
			log.info("Test: Acto obtenido. Identificador: " + show.getId() + ". Nombre: " + show.getName());
			
			final Performance performance = Performance.builder().date(LocalDate.now()).time(LocalTime.now()).remainingSeats(100L).build();
			showRepository.addShowPerformance(showId, performance);
			
			final Optional<Show> actualShow = showRepository.findShowById(3L);

			assertThat(show.getPerformances()).isEmpty();
			assertThat(actualShow.get().getPerformances()).isNotNull();
		} else {
			log.error("No existe el acto con id " + showId);
		}
	}
	
	/**
	 * Test que busca actos por nombre, categoria y fecha de actuacion.
	 */
	@Test
	void find_shows() {
		final List<Show> shows = showRepository.findShows(CATS_SHOW, CATEGOY_ID_THEATER, DATE_PERFORMANCE, Status.CREATED);

		assertThat(shows).isNotEmpty();
		assertThat(shows.get(0).getPerformances()).isNotNull();
	}

	/**
	 * Test que guarda un comentario y la busca una vez persistido.
	 */
	@Test
	void add_show_comment() {
		log.info("Test: add_show_comment()");
		final Long showId = 3L;
		final Optional<Show> optionalShow = showRepository.findShowById(3L);
		if(optionalShow.isPresent()) {
			final Show show =  optionalShow.get();
			log.info("Test: Acto obtenido. Identificador: " + show.getId() + ". Nombre: " + show.getName());

			final ShowComment showComment = ShowComment.builder().comment("Comment").build();
			showRepository.addShowComment(showId, showComment);

			final Optional<Show> actualShow = showRepository.findShowById(3L);

			assertThat(show.getComments()).isEmpty();
			assertThat(actualShow.get().getComments()).isNotNull();
		} else {
			log.error("No existe el acto con id " + showId);
		}
	}
}
