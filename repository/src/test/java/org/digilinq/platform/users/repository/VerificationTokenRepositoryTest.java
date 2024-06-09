package org.digilinq.platform.users.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.digilinq.platform.users.to.UserEntity;
import org.digilinq.platform.users.to.VerificationTokenEntity;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class VerificationTokenRepositoryTest {

    public static final String USERNAME = "someone@domain.org";
    public static final String EMAIL = "someone@domain.org";
    public static final String ENCRYPTED_PASSWORD = "$2a$10$TQ30tl1iFnMLQfSx6fou3.rHdOHb8/mwEKzW7Bv/bfI9PkWlgn1eC";


    @Autowired
    private EntityManager em;


    @Test
    void when_save_verification_token_with_empty_user_should_throw_exception() {
        VerificationTokenEntity verificationToken = VerificationTokenEntity.builder().build();
        em.persist(verificationToken);

        Assertions.assertThrows(ConstraintViolationException.class, () -> em.flush());
    }

    @Test
    void given_transient_user_when_save_verification_token_then_throw_illegal_state_exception() {
        UserEntity user = UserEntity.builder().username(USERNAME).email(EMAIL)
                .encryptedPassword(ENCRYPTED_PASSWORD).build();

        VerificationTokenEntity verificationToken = VerificationTokenEntity.builder()
                .user(user).build();


        Throwable exception = Assertions.assertThrows(IllegalStateException.class,
                () -> em.persist(verificationToken)
        );

        assertEquals(TransientPropertyValueException.class, exception.getCause().getClass());
        assertTrue(exception.getCause() instanceof PersistenceException);
    }

    @Test
    void should_save_verification_token() {
        UserEntity user = UserEntity.builder().username(USERNAME).email(EMAIL)
                .encryptedPassword(ENCRYPTED_PASSWORD).build();

        em.persist(user);
        Assertions.assertNotNull(user.getId());

        VerificationTokenEntity verificationToken = VerificationTokenEntity.builder()
                .user(user).build();
        em.persist(verificationToken);
        Assertions.assertNotNull(verificationToken.getId());
    }
}