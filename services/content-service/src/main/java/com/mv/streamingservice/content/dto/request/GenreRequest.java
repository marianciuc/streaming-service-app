package com.mv.streamingservice.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record GenreRequest(
        UUID id,
        @NotNull(message = "Name should be not empty")
        @NotEmpty(message = "Name should be not empty")
        @NotBlank(message = "Name should be not empty")
        @Length(min = 5, max = 200, message = "Name length should be between 5 and 200")
        String name,

        @NotNull(message = "Description should be not empty")
        @NotEmpty(message = "Description should be not empty")
        @NotBlank(message = "Description should be not empty")
        @Length(min = 5, message = "Description length should be more than 5")
        String description
) {
}
