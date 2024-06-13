package org.digilinq.platform.users.web.error;

import org.digilinq.platform.users.exceptions.EmailAlreadyExistsException;
import org.digilinq.platform.users.exceptions.PasswordNotMatchException;
import org.digilinq.platform.users.exceptions.UserNotFoundException;
import org.digilinq.platform.users.exceptions.UsernameAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        ProblemDetail body = super.createProblemDetail(ex, HttpStatus.NOT_FOUND, "User not found", "user not found for given id {0}", new Object[]{1L}, request);
        body.setType(URI.create("http://my-app-host.com/errors/not-found"));
        body.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
        return super.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordNotMatchException.class)
    protected ProblemDetail handlePasswordNotMatch(PasswordNotMatchException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    protected ProblemDetail handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        var body = new ResponseError(ex.getMessage());
        return super.handleExceptionInternal(ex, body, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

