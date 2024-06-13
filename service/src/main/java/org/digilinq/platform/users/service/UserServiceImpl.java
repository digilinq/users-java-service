package org.digilinq.platform.users.service;

import com.eightbits.shared.stdlib.streams.With;
import lombok.RequiredArgsConstructor;
import org.digilinq.platform.users.api.MailService;
import org.digilinq.platform.users.api.UserService;
import org.digilinq.platform.users.dto.User;
import org.digilinq.platform.users.exceptions.EmailAlreadyExistsException;
import org.digilinq.platform.users.exceptions.UserNotFoundException;
import org.digilinq.platform.users.exceptions.UsernameAlreadyExistsException;
import org.digilinq.platform.users.mapping.UserEntityMapper;
import org.digilinq.platform.users.repository.UserRepository;
import org.digilinq.platform.users.specifications.UserSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserEntityMapper mapper;
    private final MailService mailService;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User findUserById(UUID userId) {
        return repository.findById(userId).map(mapper::map).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return repository.findAll(pageable).map(mapper::map);
    }

    @Override
    public User saveUser(User user) {
        validateUser(user);

        User savedUser = With.value(user).map(mapper::map).perform(repository::save)
                .map(mapper::map).orElse(null);

        sendVerificationEmail(savedUser);

        return savedUser;
    }

    void validateUser(User user) {
        if (repository.existsByUsername(user.username()))
            throw new UsernameAlreadyExistsException(MessageFormat.format(
                    "Username {0} is already exists", user.username()));
        if (repository.exists(UserSpecification.emailEqualsIgnoreCase(user.email())))
            throw new EmailAlreadyExistsException(MessageFormat.format(
                    "Email {0} is already exists", user.email()));
    }

    void sendVerificationEmail(User user) {
        try {
            logger.info("Sending verification email to {}", user.email());
            String subject = "Welcome to Helmataart! Please Confirm Your Registration";

            mailService.sendEmail(user, subject, "verification-email");
        } catch (Exception e) {
            logger.error("Error while sending email", e);
        }
    }
}
