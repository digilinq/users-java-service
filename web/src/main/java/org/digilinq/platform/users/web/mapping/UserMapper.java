package org.digilinq.platform.users.web.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.digilinq.platform.users.generated.v1.model.CreateUserResponse;
import org.digilinq.platform.users.generated.v1.model.SignupRequest;
import org.digilinq.platform.users.generated.v1.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.net.URI;

@Mapper(uses = EncryptedPasswordMapper.class)
public interface UserMapper {
    UserAccount map(User user);

    User map(UserAccount userAccount);

    @Mapping(target = "username", source = "email")
    @Mapping(target = "encryptedPassword", source = "password", qualifiedBy = EncodedMapping.class)
    User map(CreateUserRequest createUserRequest);

    CreateUserResponse mapToCreateUserResponse(User user);

    @Mapping(target = "username", source = "email")
    @Mapping(target = "encryptedPassword", source = "password", qualifiedBy = EncodedMapping.class)
    User map(SignupRequest signupRequest);

    default URI mapToURI(User user) {
        return URI.create("users/" + user.id());
    }
}
