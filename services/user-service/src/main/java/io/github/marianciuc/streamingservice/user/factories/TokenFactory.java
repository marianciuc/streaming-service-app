/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenFactory.java
 *
 */

package io.github.marianciuc.streamingservice.user.factories;

import io.github.marianciuc.streamingservice.user.dto.Token;

import java.util.function.Function;

public interface TokenFactory extends Function<Token, Token> {
}
