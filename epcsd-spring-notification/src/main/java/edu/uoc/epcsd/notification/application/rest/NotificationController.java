package edu.uoc.epcsd.notification.application.rest;

import edu.uoc.epcsd.notification.domain.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> sendShowCreated(@PathVariable Long id) {
        log.trace("sendShowCreated");

        return new ResponseEntity<>(notificationService.notifyShowCreation(id), HttpStatus.OK);
    }
}
