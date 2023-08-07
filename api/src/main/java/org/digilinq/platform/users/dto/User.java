package org.digilinq.platform.users.dto;

import java.io.Serializable;

public record User(
        Long id,
        String username,
        String password
) implements Serializable {
}
