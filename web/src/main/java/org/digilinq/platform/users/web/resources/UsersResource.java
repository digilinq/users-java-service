package org.digilinq.platform.users.web.resources;

import lombok.RequiredArgsConstructor;
import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.generated.v1.api.UsersApi;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.digilinq.platform.users.generated.v1.model.CreateUserResponse;
import org.digilinq.platform.users.generated.v1.model.Credential;
import org.digilinq.platform.users.generated.v1.model.UserAccount;
import org.digilinq.platform.users.web.mapping.RegisterUserMapper;
import org.digilinq.platform.users.web.mapping.UserMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UsersResource implements UsersApi {

    private final UserService service;
    private final UserMapper mapper;
    private final RegisterUserMapper registerUserMapper;
    private final Logger logger;

    @Override
    public ResponseEntity<UserAccount> findUser(String userId) {
        logger.info("Find user by id {}", userId);
        var user = service.findUserById(Long.valueOf(userId));
        return ResponseEntity.ok(mapper.map(user));
    }

    @Override
    public ResponseEntity<List<UserAccount>> findUsers(String username, String email) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Credential>> getUserCredentials(UUID userId) {
        return UsersApi.super.getUserCredentials(userId);
    }

    @Override
    public ResponseEntity<CreateUserResponse> saveUser(CreateUserRequest createUserRequest) {
        var user = registerUserMapper.map(createUserRequest);
        URI location = URI.create(String.format("/users/%s", user.id()));
        return ResponseEntity.created(location).body(registerUserMapper.map(user));
    }
}
