package org.digilinq.platform.users.to;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "USER_SEQUENCE_GENERATOR", sequenceName = "USER_SEQ")
@Table(schema = "e_users", name = "security_users")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE_GENERATOR")
    @Id
    private Long id;
    private String username;
    private String encryptedPassword;
}
