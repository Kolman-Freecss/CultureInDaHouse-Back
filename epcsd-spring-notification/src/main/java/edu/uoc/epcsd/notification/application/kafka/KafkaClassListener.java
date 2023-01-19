package edu.uoc.epcsd.notification.application.kafka;

import edu.uoc.epcsd.notification.domain.service.NotificationService;
import edu.uoc.epcsd.notification.domain.Show;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KafkaClassListener {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = KafkaConstants.SHOW_TOPIC + KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_ADD, groupId = "group-1")
    void showAdded(Show show) {
        log.trace("showAdded");

        notificationService.notifyShowCreation(show);
    }
}
