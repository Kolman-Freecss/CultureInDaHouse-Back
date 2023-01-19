package edu.uoc.epcsd.notification.domain.service;


import edu.uoc.epcsd.notification.domain.Category;
import edu.uoc.epcsd.notification.domain.User;

import java.util.Set;

public interface UserService {

    Set<User> getUsersByFavouriteCategory(Category category);

}
