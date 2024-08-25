package com.mv.streamingservice.user.handler;

import java.time.LocalDateTime;

public record ExceptionResponse(
        LocalDateTime timestamp,
        String message
) {
}
