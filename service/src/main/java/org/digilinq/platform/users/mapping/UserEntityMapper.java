package org.digilinq.platform.users.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.to.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface UserEntityMapper {

    @Mapping(target = "userId", source = "id")
    User map(UserEntity userEntity);

    @Mapping(target = "id", source = "userId")
    UserEntity map(User user);

    List<User> map(List<UserEntity> userEntities);

    Object map(Page<UserEntity> userEntities);
}
