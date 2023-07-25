package org.digilinq.platform.users.web.mapping;

import org.digilinq.platform.users.generated.v1.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User map(org.digilinq.platform.users.dto.User user);

}
