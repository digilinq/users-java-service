package org.digilinq.platform.users.service;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.mapping.UserEntityMapper;
import org.digilinq.platform.users.repository.UserRepository;
import org.digilinq.platform.users.to.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USERNAME = "username";
    private static final String EMAIL = "someone@example.com";
    private static final String ENCRYPTED_PASSWORD = "$2a$08$8hrBAYsewyjE9byRe7jAwuQfASi2ujNrFgjF1QHJ9wPJHTiHmRqb.";

    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserEntityMapper mapper = Mappers.getMapper(UserEntityMapper.class);

    private UserServiceImpl userService = new UserServiceImpl(userRepository, mapper);

    @Test
    void should_call_user_repository_with_correct_parameters_and_return_user_with_id() {
        User user = new User(null, USERNAME, ENCRYPTED_PASSWORD, EMAIL);

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(UserEntity.builder()
                .id(USER_ID)
                .username(USERNAME)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .email(EMAIL).build()
        );

        User savedUser = userService.saveUser(user);

        Mockito.verify(userRepository).save(
                ArgumentMatchers.argThat(userEntity -> userEntity.getId() == null &&
                        userEntity.getEmail().equals(EMAIL) &&
                        userEntity.getUsername().equals(USERNAME) &&
                        userEntity.getEncryptedPassword().equals(ENCRYPTED_PASSWORD)
                ));

        assertEquals(USER_ID, savedUser.userId());
        assertEquals(USERNAME, savedUser.username());
        assertEquals(ENCRYPTED_PASSWORD, savedUser.encryptedPassword());
        assertEquals(EMAIL, savedUser.email());
    }
}
