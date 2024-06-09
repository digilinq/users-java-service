package org.digilinq.platform.users.web.resources;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.configuration.Metrics;
import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.generated.v1.api.UsersApi;
import org.digilinq.platform.users.generated.v1.model.CreateUserRequest;
import org.digilinq.platform.users.generated.v1.model.CreateUserResponse;
import org.digilinq.platform.users.generated.v1.model.Credential;
import org.digilinq.platform.users.generated.v1.model.UserAccount;
import org.digilinq.platform.users.web.mapping.UserMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.digilinq.platform.users.configuration.HeaderConstants.TOTAL_ELEMENTS;
import static org.digilinq.platform.users.configuration.HeaderConstants.TOTAL_PAGES;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UsersResource implements UsersApi {

    private final UserService service;
    private final UserMapper mapper;
    private final Logger logger;
    private final Metrics metrics;

    @Override
    public ResponseEntity<UserAccount> findUser(String userId) {
        logger.info("Find user by id {}", userId);
        var user = service.findUserById(UUID.fromString(userId));
        return ResponseEntity.ok(mapper.map(user));
    }

    @Override
    @Timed(value = "get.all.time", description = "Time taken to return all users")
    public ResponseEntity<List<UserAccount>> findUsers(String username, String email, Integer page, Integer size) {
        logger.info("Getting all users");

        Page<UserAccount> users = service.findAll(page, size).map(mapper::map);

        var headers = new HttpHeaders();
        headers.add(TOTAL_PAGES, String.valueOf(users.getTotalPages()));
        headers.add(TOTAL_ELEMENTS, String.valueOf(users.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(users.getContent());
    }

    @Override
    public ResponseEntity<List<Credential>> getUserCredentials(UUID userId) {
        return UsersApi.super.getUserCredentials(userId);
    }

    @Override
    public ResponseEntity<CreateUserResponse> saveUser(CreateUserRequest createUserRequest) {
        var user = mapper.map(createUserRequest);
        User savedUser = service.saveUser(user);

        metrics.getSignup().increment();

        URI location = URI.create(String.format("/users/%s", user.userId()));
        return ResponseEntity.created(location).body(mapper.mapToCreateUserResponse(savedUser));
    }
}
