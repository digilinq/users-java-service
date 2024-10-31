package org.digilinq.platform.users.dto;

import java.io.Serializable;
import java.util.UUID;

public record User(
        UUID id,
        String username,
        String encryptedPassword,
        String email
) implements Serializable {
}
