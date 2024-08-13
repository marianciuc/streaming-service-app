package com.mv.streamingservice.content.service;

import com.mv.streamingservice.content.dto.response.SeasonResponse;
import com.mv.streamingservice.content.entity.Content;
import com.mv.streamingservice.content.entity.Season;
import com.mv.streamingservice.content.mappers.SeasonMapper;
import com.mv.streamingservice.content.repository.SeasonRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * @param number The season number.
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
     * @param contentId     The UUID of the content.
     * @param seasonNumber  The season number.
     * @return A list of SeasonResponse objects.
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
}
