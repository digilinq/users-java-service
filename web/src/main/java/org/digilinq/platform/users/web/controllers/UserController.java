package org.digilinq.platform.users.web.controllers;

import com.eightbits.shared.stdlib.command.CommandBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.exceptions.PasswordNotMatchException;
import org.digilinq.platform.users.generated.v1.api.SignupApi;
import org.digilinq.platform.users.generated.v1.model.SignupRequest;
import org.digilinq.platform.users.web.mapping.UserMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.function.Function;

@RestController
public class UserController implements SignupApi {

    private final UserService userService;
    private final UserMapper mapper;
    private final Logger logger;
    private final HttpServletRequest request;
    private final HttpServletRequest httpServletRequest;

    public UserController(
            UserService userService,
            UserMapper mapper,
            Logger logger,
            HttpServletRequest request,
            HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.mapper = mapper;
        this.logger = logger;
        this.request = request;
        this.httpServletRequest = httpServletRequest;
    }

    @CrossOrigin
    @Override
    public ResponseEntity<Void> signup(SignupRequest signupRequest) {
        logger.info("Sign up a user " + httpServletRequest.getRequestURL());

        var signupCommand = CommandBuilder.of(userService::saveUser)
                .compose((Function<SignupRequest, User>) mapper::map)
                .andThen(mapper::mapToURI)
                .andThen(ResponseEntity::created);

        if (!Objects.equals(signupRequest.getPassword(), signupRequest.getConfirmPassword()))
            throw new PasswordNotMatchException("Password not match");

        return signupCommand.execute(signupRequest).build();
    }
}
