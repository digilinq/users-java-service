package org.digilinq.platform.users.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.to.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface UserEntityMapper {

    User map(UserEntity userEntity);

    UserEntity map(User user);

    List<User> map(List<UserEntity> userEntities);

    Object map(Page<UserEntity> userEntities);
}
