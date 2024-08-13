package com.mv.streamingservice.content.service;

import com.mv.streamingservice.content.dto.response.EpisodeResponse;
import com.mv.streamingservice.content.entity.Content;
import com.mv.streamingservice.content.entity.Episode;
import com.mv.streamingservice.content.entity.Season;
import com.mv.streamingservice.content.mappers.EpisodeMapper;
import com.mv.streamingservice.content.repository.EpisodeRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;
    private final ContentService contentService;
    private final SeasonService seasonService;


    // TODO create, update, delete episodes

    /**
     * Finds an episode in a season based on the episode number.
     *
     * @param season the season to search for the episode
     * @param number the episode number to search for
     * @return the found episode as an EpisodeResponse object
     * @throws NotFoundException if the episode is not found
     */
    public Episode findEpisodeByNumber(Season season, Integer number) {
        return season.getEpisodes().stream()
                .filter(e -> e.getEpisodeNumber().equals(number))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Episode not found :: " + number));
    }

    /**
     * Finds an episode by the content ID, season number, and episode number.
     *
     * @param contentId      the unique ID of the content
     * @param seasonNumber   the season number
     * @param episodeNumber  the episode number
     * @return the episode information as an EpisodeResponse object
     * @throws NotFoundException if the episode is not found
     */
    public EpisodeResponse findEpisodeByContentIdAndSeasonNumberAndEpisodeNumber(UUID contentId, Integer seasonNumber, Integer episodeNumber) {
        Content content = contentService.getContentById(contentId);
        contentService.assertContentTypeIsSeries(content);
        Season season = seasonService.findSeasonByContentAndNumber(content, seasonNumber);
        Episode episode = this.findEpisodeByNumber(season, episodeNumber);
        return episodeMapper.toDto(episode);
    }

}
