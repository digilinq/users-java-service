package org.digilinq.platform.users.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.to.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {UserEntityMapperImpl.class})
class UserEntityMapperTest {

    private static final UUID USER_ID = UUID.fromString("399fdb30-0b1f-11ef-9262-0242ac120002");
    private static final String USERNAME = "username";
    private static final String ENCRYPTED_PASSWORD = "encrypted_password";
    private static final String EMAIL = "user@example.org";

    @Autowired
    private UserEntityMapper mapper;

    private static Stream<Arguments> provideUserId() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(USER_ID)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUserId")
    void should_map_user_dto_to_user_entity(UUID userId) {
        User user = new User(userId, USERNAME, ENCRYPTED_PASSWORD, EMAIL);
        var userEntity = mapper.map(user);
        assertEquals(userId, userEntity.getId());
        assertEquals(USERNAME, userEntity.getUsername());
        assertEquals(ENCRYPTED_PASSWORD, userEntity.getEncryptedPassword());
        assertEquals(EMAIL, userEntity.getEmail());
    }

    @Test
    void should_map_user_entity_to_user_dto() {
        UserEntity userEntity = UserEntity.builder()
                .id(USER_ID)
                .username(USERNAME)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .email(EMAIL)
                .build();
        var user = mapper.map(userEntity);
        assertEquals(USER_ID, user.id());
        assertEquals(USERNAME, user.username());
        assertEquals(ENCRYPTED_PASSWORD, user.encryptedPassword());
        assertEquals(EMAIL, user.email());
    }
}