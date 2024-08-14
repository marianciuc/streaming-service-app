package com.mv.streamingservice.content.service;

import com.mv.streamingservice.content.dto.request.SeasonRequest;
import com.mv.streamingservice.content.dto.response.SeasonResponse;
import com.mv.streamingservice.content.entity.Content;
import com.mv.streamingservice.content.entity.Season;
import com.mv.streamingservice.content.enums.RecordStatus;
import com.mv.streamingservice.content.exceptions.NotFoundException;
import com.mv.streamingservice.content.mappers.SeasonMapper;
import com.mv.streamingservice.content.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * The SeasonService class is responsible for handling operations related to seasons.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SeasonService {

    private static final String CONTENT_NOT_FOUND_ERROR = "Season service error :: content not found :: ";

    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;
    private final ContentService contentService;

    /**
     * Finds a Season based on the given Content and season number.
     *
     * @param content The Content object.
     * @param number  The season number.
     * @return The found Season.
     * @throws NotFoundException If the Season is not found.
     */
    public Season findSeasonByContentAndNumber(Content content, Integer number) {
        return content.getSeasons().stream()
                .filter(s -> s.getSeasonNumber().equals(number))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(CONTENT_NOT_FOUND_ERROR + number));
    }


    /**
     * Retrieves a list of SeasonResponse objects based on the content ID and season number.
     *
     * @param contentId    The UUID of the content.
     * @param seasonNumber The season number.
     * @return A list of {@code SeasonResponse} objects.
     */
    public List<SeasonResponse> getSeasonsByContentId(UUID contentId, Integer seasonNumber) {
        Content content = contentService.getContentById(contentId);
        contentService.assertContentTypeIsSeries(content);

        if (seasonNumber == null) return content.getSeasons().stream().map(seasonMapper::toDto).toList();

        Season season = this.findSeasonByContentAndNumber(content, seasonNumber);
        SeasonResponse seasonResponse = seasonMapper.toDto(season);
        List<SeasonResponse> seasonResponseList = new ArrayList<>();
        seasonResponseList.add(seasonResponse);
        return seasonResponseList;
    }

    /**
     * Creates a new season for a content.
     *
     * @param contentId     The UUID of the content.
     * @param seasonRequest The SeasonRequest object containing the season details.
     * @return The UUID of the created season.
     */
    public UUID createSeason(UUID contentId, SeasonRequest seasonRequest) {
        Content content = contentService.getContentById(contentId);
        Season season = seasonMapper.toEntity(seasonRequest);

        int maxSeasonNumber = content.getSeasons().isEmpty() ? 0 : content.getSeasons().stream()
                .max(Comparator.comparing(Season::getSeasonNumber))
                .get()
                .getSeasonNumber();

        season.setSeasonNumber(maxSeasonNumber + 1);
        season.setContent(content);
        return seasonRepository.save(season).getId();
    }

    /**
     * Updates the season with the given seasonId using the information from the seasonRequest.
     *
     * @param seasonId      the unique identifier of the season
     * @param seasonRequest the season request containing the updated information
     * @return the {@code UUID} of the updated season
     */
    public UUID updateSeason(UUID seasonId, SeasonRequest seasonRequest) {
        Season season = this.getSeasonById(seasonId);
        this.updateIfValueChanged(seasonRequest.seasonTitle(), season.getSeasonTitle(), season::setSeasonTitle);
        this.updateIfValueChanged(seasonRequest.releaseDate(), season.getSeasonReleaseDate(), season::setSeasonReleaseDate);

        return seasonRepository.save(season).getId();
    }

    /**
     * Retrieves a season by its ID.
     *
     * @param seasonId the ID of the season to retrieve
     * @return the retrieved Season object
     * @throws NotFoundException if the season with the specified ID is not found or is marked as deleted
     */
    public Season getSeasonById(UUID seasonId) {
        return seasonRepository.findByIdAndRecordStatusNot(seasonId, RecordStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(CONTENT_NOT_FOUND_ERROR + seasonId));
    }

    /**
     * Updates the value of a field if the requested value differs from the entity value.
     * This method checks if the value has changed using the hasValueChanged method,
     * and if it has, it calls the setter function to update the value.
     *
     * @param <T>          the type of the value
     * @param requestValue the requested value
     * @param entityValue  the entity value
     * @param setter       the setter function that updates the value
     */
    private <T> void updateIfValueChanged(T requestValue, T entityValue, Consumer<T> setter) {
        if (requestValue == null && entityValue != null ||
                requestValue != null && !requestValue.equals(entityValue)) {
            setter.accept(requestValue);
        }
    }

    /**
     * Checks if the given value has changed from the current value.
     *
     * @param newValue The new value to compare.
     * @param currentValue The current value to compare.
     * @return {@code true} if the value has changed, {@code false} otherwise.
     */
    private <T> boolean hasValueChanged(T newValue, T currentValue) {
        return !newValue.equals(currentValue);
    }

    /**
     * Deletes a season identified by the given seasonId.
     *
     * @param seasonId the UUID of the season to be deleted
     */
    public void deleteSeason(UUID seasonId) {
        Season season = this.getSeasonById(seasonId);
        season.getEpisodes().forEach((episode -> episode.setRecordStatus(RecordStatus.DELETED)));
        seasonRepository.save(season);
    }
}
