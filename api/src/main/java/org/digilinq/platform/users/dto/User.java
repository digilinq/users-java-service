package org.digilinq.platform.users.dto;

public record User(
        Long id,
        String username,
        String password
) {
}
