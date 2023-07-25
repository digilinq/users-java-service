package org.digilinq.platform.users.api;

import org.digilinq.platform.users.dto.User;

import java.util.Optional;

public interface UserService {
    User findUserById(Long userId);

    User saveUser(User user);
}
