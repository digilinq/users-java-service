package org.digilinq.platform.users.service;

import lombok.RequiredArgsConstructor;
import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.exceptions.UserNotFoundException;
import org.digilinq.platform.users.mapping.UserEntityMapper;
import org.digilinq.platform.users.repository.UserRepository;
import org.digilinq.platform.users.util.With;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserEntityMapper mapper;

    @Override
    public User findUserById(Long userId) {
        return repository.findById(userId).map(mapper::map).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveUser(User user) {
        return With.value(user).map(mapper::map).perform(repository::save)
                .map(mapper::map).orElse(null);
    }
}
