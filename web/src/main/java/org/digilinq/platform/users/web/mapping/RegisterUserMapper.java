package org.digilinq.platform.users.web.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.digilinq.platform.users.generated.v1.model.CreateUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = EncryptedPasswordMapper.class)
public interface RegisterUserMapper {

    @Mapping(target = "username", source = "email")
    @Mapping(target = "encryptedPassword", source = "password", qualifiedBy = EncodedMapping.class)
    User map(CreateUserRequest createUserRequest);

    CreateUserResponse map(User user);
}
