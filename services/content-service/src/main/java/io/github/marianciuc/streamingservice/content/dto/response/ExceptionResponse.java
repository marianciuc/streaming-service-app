package io.github.marianciuc.streamingservice.content.dto.response;

import java.time.LocalDateTime;

public record ExceptionResponse (
        String message,
        LocalDateTime time
) {
}
