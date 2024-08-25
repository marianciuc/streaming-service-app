package com.mv.streamingservice.user.exceptions;

/**
 * The UserIsBanned class is a custom RuntimeException that is thrown when a user is banned from accessing certain resources.
 */
public class UserIsBanned extends RuntimeException {
    public UserIsBanned(String message) {
    }
}
