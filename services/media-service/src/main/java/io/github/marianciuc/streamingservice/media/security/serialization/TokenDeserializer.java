/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenDeserializer.java
 *
 */

package io.github.marianciuc.streamingservice.media.security.serialization;


import io.github.marianciuc.streamingservice.media.security.dto.Token;

import java.util.function.Function;

public interface TokenDeserializer extends Function<String, Token> {
}
