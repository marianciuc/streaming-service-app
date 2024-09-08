/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AuthenticationTokenFactory.java
 *
 */

package io.github.marianciuc.streamingservice.user.factories;

import io.github.marianciuc.streamingservice.user.dto.common.Token;
import org.springframework.security.core.Authentication;

import java.util.function.Function;

public interface AuthenticationTokenFactory extends Function<Authentication, Token> {
}
