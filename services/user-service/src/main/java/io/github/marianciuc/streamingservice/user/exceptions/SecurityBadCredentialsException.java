package io.github.marianciuc.streamingservice.user.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * The SecurityBadCredentialsException class is a custom RuntimeException that is thrown when
 * the provided credentials for user authentication are invalid.
 */
public class SecurityBadCredentialsException extends RuntimeException {
    public SecurityBadCredentialsException(String message, BadCredentialsException e) {
        super(message, e);
    }
}
