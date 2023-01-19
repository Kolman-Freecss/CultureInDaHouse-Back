package edu.uoc.epcsd.showcatalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase de test de dominio Show que verifica que al crear un Show y cancelarlo, su Status final es CANCELLED.
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
public class ShowUnitTest {

	@Test
	public void should_change_status_when_canceled() {
		log.debug("Test: should_change_status_when_canceled()");

		Show actualShow = Show.builder().name("Kiss - The final tour").build();
		log.debug("Creado el acto con nombre: " + actualShow.getName());
		log.debug("El estado del acto es: " + actualShow.getStatus());

		actualShow.cancel();
		log.debug("Cancelado el acto con nombre: " + actualShow.getName());
		log.debug("El estado del acto es: " + actualShow.getStatus());

		assertThat(actualShow.getStatus()).isEqualTo(Status.CANCELLED);
	}
	@Test
	public void get_average_rating() {
		log.debug("Test: get_average_rating()");

		HashSet<ShowComment> comments = new HashSet<>();
		comments.add(ShowComment.builder().rating(1).build());
		comments.add(ShowComment.builder().rating(2).build());

		Show actualShow = Show.builder().name("Kiss - The final tour").comments(comments).build();
		log.debug("Creado el acto con nombre: " + actualShow.getName());
		log.debug("Tiene dos comentarios con ratin 1 y 2 respectivamente");

		float rating = actualShow.getAverageRating();

		assertThat(rating).isEqualTo(1.5f);
	}
}
