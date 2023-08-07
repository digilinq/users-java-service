package org.digilinq.platform.users.web.mapping;

import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.generated.v1.model.UserAccount;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserAccount map(User user);

    User map (UserAccount userAccount);
}
