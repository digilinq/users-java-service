package org.digilinq.platform.users.to;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.util.UUID;

@StaticMetamodel(UserEntity.class)
public class UserEntity_ {
    public static volatile SingularAttribute<UserEntity, UUID> id;
    public static volatile SingularAttribute<UserEntity, String> username;
    public static volatile SingularAttribute<UserEntity, String> encryptedPassword;
    public static volatile SingularAttribute<UserEntity, String> email;
    public static volatile SingularAttribute<UserEntity, Boolean> activated;
}