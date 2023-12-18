package org.digilinq.platform.users.specification;

import org.digilinq.platform.users.to.UserEntity;
import org.digilinq.platform.users.to.UserEntity_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> emailEqualsIgnoreCase(String email) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get(UserEntity_.email)), email.toLowerCase());
    }

    public static Specification<UserEntity> usernameEqualsIgnoreCase(String username) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get(UserEntity_.username)), username.toLowerCase());
    }
}
