package org.digilinq.platform.users.exceptions;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException() {
        super();
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }
}
