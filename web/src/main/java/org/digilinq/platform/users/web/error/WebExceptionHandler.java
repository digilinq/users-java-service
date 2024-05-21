package org.digilinq.platform.users.web.error;

import org.digilinq.platform.users.exceptions.PasswordNotMatchException;
import org.digilinq.platform.users.exceptions.UserNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        var body = new ResponseError(ex.getMessage());
        return super.handleExceptionInternal(ex, body, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

