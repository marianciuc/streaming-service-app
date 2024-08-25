package io.github.marianciuc.streamingservice.user.dto;

import java.time.LocalDateTime;

public record ExceptionResponse(
        LocalDateTime timestamp,
        String message
) {
}
