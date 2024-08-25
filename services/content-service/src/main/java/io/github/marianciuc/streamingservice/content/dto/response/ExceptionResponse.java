package com.mv.streamingservice.content.dto.response;

import java.time.LocalDateTime;

public record ExceptionResponse (
        String message,
        LocalDateTime time
) {
}
