package edu.uoc.epcsd.notification;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.uoc.epcsd.notification.domain.service.NotificationService;

@SpringBootTest
class NotificationApplicationTests {

	/** Servicio de notificacion. */
	@Autowired
	private NotificationService notificationService;

	@Test
	void contextLoads() {
		// comprobacion que el servicio de catalogo se encuentra en el contexto
		assertThat(notificationService).isNotNull();
	}
}
