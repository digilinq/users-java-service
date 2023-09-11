package org.digilinq.platform.users.to;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "USER_SEQUENCE_GENERATOR", sequenceName = "USER_SEQ")
@Table(schema = "e_users", name = "security_users")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE_GENERATOR")
    @Id
    private Long id;

    private String username;
    private String encryptedPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
