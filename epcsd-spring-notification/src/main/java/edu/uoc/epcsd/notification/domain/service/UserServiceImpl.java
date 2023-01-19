package edu.uoc.epcsd.notification.domain.service;

import edu.uoc.epcsd.notification.domain.Category;
import edu.uoc.epcsd.notification.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private final Random random = new Random();

    private final MockNeat mock = MockNeat.threadLocal();

    private final MockUnit<User> userGenerator = mock.filler(User::new)
            .setter(User::setFullName, mock.names().full())
            .setter(User::setId, mock.longs())
            .setter(User::setEmail, mock.emails())
            .setter(User::setMobileNumber, mock.regex("\\\\d{3}\\\\d{3}\\\\d{3}"));

    // mock
    public Set<User> getUsersByFavouriteCategory(Category category) {
        log.trace("Retrieving users with favourite category '" + category.getName() + "'");

        Set<User> users = new HashSet<>();

        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            users.add(userGenerator.get());
        }

        return users;
    }
}
