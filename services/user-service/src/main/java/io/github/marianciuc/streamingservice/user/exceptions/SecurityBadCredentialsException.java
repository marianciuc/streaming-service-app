package io.github.marianciuc.streamingservice.user.exceptions;

/**
 * The SecurityBadCredentialsException class is a custom RuntimeException that is thrown when
 * the provided credentials for user authentication are invalid.
 */
public class SecurityBadCredentialsException extends RuntimeException {
    public SecurityBadCredentialsException(String message) {
        super(message);
    }
}
