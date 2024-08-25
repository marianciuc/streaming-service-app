package io.github.marianciuc.streamingservice.subscription.dto;


import io.github.marianciuc.streamingservice.subscription.entity.Currency;
import io.github.marianciuc.streamingservice.subscription.entity.Resolution;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Validated
public record SubscriptionRequest(
        @NotEmpty(message = "Name cannot be empty")
        String name,

        @Size(max = 200, message = "Description can't be longer than 200 characters")
        @NotEmpty(message = "Description cannot be empty")
        String description,

        @PositiveOrZero(message = "Duration must be zero or positive number")
        Integer durationInDays,

        @Positive(message = "Duration must be a positive number")
        Integer allowedActiveSessions,

        @NotNull(message = "Price cannot be null")
        BigDecimal price,

        @NotNull(message = "Currency cannot be null")
        Currency currency,

        @NotNull(message = "Allowed resolutions list cannot be null")
        List<@NotNull(message = "Resolution in the list cannot be null") Resolution> allowedResolutions,
        UUID nextSubscriptionId,
        Boolean isTemporary
) {
}
