package org.digilinq.platform.users.web.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.digilinq.platform.users.generated.v1.model.SignupRequest;
import org.digilinq.platform.users.generated.v1.model.UserAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UserMapperImpl.class, EncryptedPasswordMapper.class, BCryptPasswordEncoder.class
})
class UserMapperTest {
    public static final String USER_ID = "399fdb30-0b1f-11ef-9262-0242ac120002";
    public static final String USERNAME = "hossein.mohammadi@outlook.com";
    public static final String EMAIL = "hossein.mohammadi@outlook.com";
    public static final String PASSWORD = "123456";
    public static final String CONFIRM_PASSWORD = "123456";

    @Autowired
    private UserMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void should_map_CreateUserRequest_to_User() {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                EMAIL, PASSWORD, CONFIRM_PASSWORD);
        User user = mapper.map(createUserRequest);
        assertEquals(EMAIL, user.email());
        assertEquals(EMAIL, user.username());
        assertTrue(passwordEncoder.matches(PASSWORD, user.encryptedPassword()));
    }

    @Test
    void should_map_User_to_UserAccount() {
        User user = new User(UUID.fromString(USER_ID), EMAIL, PASSWORD, EMAIL);
        UserAccount account = mapper.map(user);

        assertEquals(UUID.fromString(USER_ID), account.getId());
        assertEquals(USERNAME, account.getUsername());
        assertEquals(EMAIL, account.getEmail());
    }

    @Test
    void should_map_SignupRequest_to_User() {
        SignupRequest signupRequest = new SignupRequest(
                EMAIL, PASSWORD, CONFIRM_PASSWORD
        );
        User user = mapper.map(signupRequest);
        assertEquals(EMAIL, user.email());
        assertEquals(EMAIL, user.username());
        assertTrue(passwordEncoder.matches(PASSWORD, user.encryptedPassword()));
    }
}
