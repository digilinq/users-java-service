package org.digilinq.platform.users.web.error;

import org.digilinq.platform.users.exceptions.UserNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserMotFound(UserNotFoundException ex, WebRequest request){
        ProblemDetail problemDetail = super.createProblemDetail(ex, HttpStatus.NOT_FOUND, "User not found", "user not found for given id {0}", new Object[]{1L}, request);
        return super.handleExceptionInternal(ex, problemDetail, null, HttpStatus.NOT_FOUND, request);
    }
}
