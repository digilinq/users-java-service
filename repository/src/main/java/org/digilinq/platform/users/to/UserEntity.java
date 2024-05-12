package org.digilinq.platform.users.to;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "security_users", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_USERS_USERNAME", columnNames = {"username"}),
        @UniqueConstraint(name = "UQ_USERS_EMAIL", columnNames = {"email"}),
})
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String email;
    private String username;
    private String encryptedPassword;
}
