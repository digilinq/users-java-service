package org.digilinq.platform.users.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.to.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserEntityMapper {

    User map(UserEntity userEntity);

    UserEntity map(User user);
}
