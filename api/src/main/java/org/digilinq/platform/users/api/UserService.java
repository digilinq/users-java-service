package org.digilinq.platform.users.api;

import org.digilinq.platform.users.dto.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {
    User findUserById(UUID userId);

    Page<User> findAll(int page, int size);

    User saveUser(User user);

    void deleteUser(UUID uuid);
}
