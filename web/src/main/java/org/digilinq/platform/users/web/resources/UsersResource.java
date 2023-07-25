package org.digilinq.platform.users.web.resources;

import lombok.RequiredArgsConstructor;
import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.generated.v1.api.UsersApi;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.digilinq.platform.users.generated.v1.model.CreateUserResponse;
import org.digilinq.platform.users.generated.v1.model.Credential;
import org.digilinq.platform.users.generated.v1.model.User;
import org.digilinq.platform.users.web.mapping.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UsersResource implements UsersApi {

    private final UserService service;
    private final UserMapper mapper;

    @Override
    public ResponseEntity<User> findUser(String userId) {
        var user = service.findUserById(Long.valueOf(userId));
        return ResponseEntity.ok(mapper.map(user));
    }

    @Override
    public ResponseEntity<List<User>> findUsers(String username, String email) {
        return UsersApi.super.findUsers(username, email);
    }

    @Override
    public ResponseEntity<List<Credential>> getUserCredentials(UUID userId) {
        return UsersApi.super.getUserCredentials(userId);
    }

    @Override
    public ResponseEntity<CreateUserResponse> saveUser(CreateUserRequest createUserRequest) {
        return UsersApi.super.saveUser(createUserRequest);
    }
}
