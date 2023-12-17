package org.digilinq.platform.users.api;

import org.digilinq.platform.users.dto.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User findUserById(Long userId);

    Page<User> findAll(int page, int size);

    User saveUser(User user);
}
