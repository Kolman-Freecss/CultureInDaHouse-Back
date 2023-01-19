package edu.uoc.epcsd.notification.application.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import edu.uoc.epcsd.notification.domain.service.NotificationService;
import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de controller NotificationController.
 *
 * @author Grupo Slack Chronicles
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = NotificationController.class)
public class NotificationControllerUnitTest {
	/** Path de la llamada api rest a categorias. */
	private static final String REST_NOTIFICATION_PATH = "/api/notifications";

	/** Mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** Mock del servicio de notificación. */
	@MockBean
	private NotificationService notificationService;

	/**
	 * Test que envia una notificacion al crear un show.
	 */
	@Test
	void send_show_created() throws Exception {
		log.info("Test: send_show_created()");
		final Long showId = 30L;
		Mockito.when(notificationService.notifyShowCreation(showId)).thenReturn(true);

		mockMvc.perform(post(REST_NOTIFICATION_PATH + "/" + showId).contentType(MediaType.APPLICATION_JSON).content("")).andDo(print()).andExpect(status().isOk());
	}
}
