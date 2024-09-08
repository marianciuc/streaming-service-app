/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenSerializer.java
 *
 */

package io.github.marianciuc.streamingservice.user.serializers;

import io.github.marianciuc.streamingservice.user.dto.Token;

import java.util.function.Function;

public interface TokenSerializer  extends Function<Token, String> {
}
