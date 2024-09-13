/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenDeserializer.java
 *
 */

package io.github.marianciuc.streamingservice.payment.security;

import io.github.marianciuc.streamingservice.payment.dto.common.Token;

import java.util.function.Function;

public interface TokenDeserializer extends Function<String, Token> {
}
