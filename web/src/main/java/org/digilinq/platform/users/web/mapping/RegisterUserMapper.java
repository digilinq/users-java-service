package org.digilinq.platform.users.web.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.digilinq.platform.users.generated.v1.model.CreateUserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface RegisterUserMapper {
    User map(CreateUserRequest createUserRequest);

    CreateUserResponse map(User user);

}
