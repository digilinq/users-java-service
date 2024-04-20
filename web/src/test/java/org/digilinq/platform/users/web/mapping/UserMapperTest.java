package org.digilinq.platform.users.web.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UserMapperImpl.class, EncryptedPasswordMapper.class, BCryptPasswordEncoder.class
})
class UserMapperTest {
    public static final String EMAIL = "hossein.mohammadi@outlook.com";
    public static final String PASSWORD = "123456";
    public static final String CONFIRM_PASSWORD = "123456";
    @Autowired
    private UserMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void shouldMapCreateUserRequestToUserDomain() {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                EMAIL, PASSWORD, CONFIRM_PASSWORD);
        User user = mapper.map(createUserRequest);
        assertEquals(EMAIL, user.email());
        assertEquals(EMAIL, user.username());
        assertTrue(passwordEncoder.matches(PASSWORD, user.encryptedPassword()));
    }
}