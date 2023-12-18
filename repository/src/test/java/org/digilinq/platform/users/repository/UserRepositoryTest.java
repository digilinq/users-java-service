package org.digilinq.platform.users.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.digilinq.platform.users.specification.UserSpecification;
import org.digilinq.platform.users.to.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    public static final String USERNAME = "someone@domain.org";
    public static final String EMAIL = "someone@domain.org";
    public static final String ENCRYPTED_PASSWORD = "$2a$10$TQ30tl1iFnMLQfSx6fou3.rHdOHb8/mwEKzW7Bv/bfI9PkWlgn1eC";


    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository repository;

    @Test
    void should_work() {
        assertNotNull(em);
    }

    @Test
    void print_table_names() {
        Metamodel metamodel = em.getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();

        entities.forEach(e -> {
            System.out.println(e.getName());
        });
    }

    @Test
    void save_user_in_database() {
        UserEntity user = UserEntity.builder().username(USERNAME).email(EMAIL)
                .encryptedPassword(ENCRYPTED_PASSWORD).build();

        em.persist(user);

        List<UserEntity> users = em.createQuery("select t from UserEntity t", UserEntity.class).getResultList();
        users.stream().map(r -> String.format("%s,%s,%s,%s",
                r.getId(), r.getUsername(), r.getEmail(), r.getEncryptedPassword()
        )).forEach(System.out::println);
    }

    @Test
    void userIdShouldGenerated() {
        UUID userId = UUID.randomUUID();

        UserEntity user = UserEntity.builder().id(userId).build();
        UserEntity savedUser = repository.save(user);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getId()).isNotEqualTo(userId);
    }

    @Test
    void usernameIsUnique() {
        UserEntity user1 = UserEntity.builder().username(USERNAME).build();
        UserEntity user2 = UserEntity.builder().username(USERNAME).build();
        repository.save(user1);
        repository.save(user2);

        assertThrows(DataIntegrityViolationException.class, () -> repository.flush());
    }

    @Test
    void itIsPossibleToSaveToUserWithEmptyUsername() {
        UserEntity user1 = UserEntity.builder().build();
        UserEntity user2 = UserEntity.builder().build();
        repository.save(user1);
        repository.save(user2);
        assertDoesNotThrow(() -> repository.flush());
    }

    @Test
    void searchByEmailShouldNotCaseSensitive() {
        UserEntity user=UserEntity.builder().email(EMAIL).build();
        repository.save(user);
        // repository.flush();
        assertThat(repository.findAll(UserSpecification.emailEqualsIgnoreCase(
                EMAIL.toUpperCase())).size()).isEqualTo(1);
    }

    @Test
    @Sql("classpath:sql/users.sql")
    void findUserByEmailExpectToFindSingleResponseOtherwiseNull() {
        assertThat(
                assertDoesNotThrow(() -> repository.findByEmail("manager@example.com"))
        ).isNull();
        UserEntity user = repository.findByEmail("info@example.com");
        assertThat(user.getUsername()).isEqualTo("u01");
        assertThat(user.getEncryptedPassword()).isEqualTo("P@ssw0rd");
    }
}