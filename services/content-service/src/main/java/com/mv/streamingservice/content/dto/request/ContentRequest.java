package com.mv.streamingservice.content.dto.request;

import com.mv.streamingservice.content.enums.ContentType;
import com.mv.streamingservice.content.enums.RecordStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Valid
public record ContentRequest(
        UUID id,

        @NotBlank(message = "Title should be not empty")
        @Length(min = 5, max = 200, message = "Title length should be between 5 and 200")
        String title,

        @NotBlank(message = "Description should be not empty")
        @Length(min = 5, message = "Description length should be more than 5")
        String description,

        @NotNull(message = "Content type should be not empty")
        ContentType contentType,

        @NotNull(message = "Release date should be not empty")
        LocalDateTime releaseDate,

        RecordStatus recordStatus,

        @NotNull(message = "Duration should be not empty")
        @Min(1)
        Integer duration,

        @NotNull(message = "Content should be not empty")
        @NotBlank(message = "Content should be not empty")
        @NotEmpty(message = "Content should be not empty")
        String ageRating,

        String posterUrl,
        List<UUID> genresIds,
        UUID movieId,
        List<UUID> seasonsIds
) {
}
