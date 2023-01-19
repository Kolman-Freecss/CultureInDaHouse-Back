package edu.uoc.epcsd.notification.domain.service;

import edu.uoc.epcsd.notification.domain.Show;

public interface NotificationService {

    boolean notifyShowCreation(Long id);

    boolean notifyShowCreation(Show show);

}
