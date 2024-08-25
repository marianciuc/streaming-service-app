package io.github.marianciuc.streamingservice.content.service;


import io.github.marianciuc.streamingservice.content.dto.request.EpisodeRequest;
import io.github.marianciuc.streamingservice.content.dto.response.EpisodeResponse;
import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.entity.Episode;
import io.github.marianciuc.streamingservice.content.entity.Season;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.mappers.EpisodeMapper;
import io.github.marianciuc.streamingservice.content.repository.EpisodeRepository;
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

    /**
     * Creates a new episode for a given content, season number, and episode request.
     *
     * @param contentId     the unique ID of the content
     * @param seasonNumber  the season number
     * @param request       the episode request
     * @return the unique ID of the created episode
     */
    public UUID createEpisode(UUID contentId, Integer seasonNumber, EpisodeRequest request) {
        Content content = contentService.getContentById(contentId);
        contentService.assertContentTypeIsSeries(content);
        Season season = seasonService.findSeasonByContentAndNumber(content, seasonNumber);
        Episode episode = episodeMapper.toEntity(request);
        episode.setSeason(season);
        return episodeRepository.save(episode).getId();
    }

    /**
     * Deletes an episode.
     *
     * @param episodeId the unique ID of the episode to be deleted
     * @throws NotFoundException if the episode is not found
     */
    public void deleteEpisode(UUID episodeId) {
        Episode episode = this.getEpisodeByID(episodeId);
        episode.setRecordStatus(RecordStatus.DELETED);
        episodeRepository.save(episode);
    }

    /**
     * Retrieves an episode by its ID.
     *
     * @param episodeId the ID of the episode to retrieve
     * @return the retrieved Episode object
     * @throws NotFoundException if the episode with the given ID is not found or has been deleted
     */
    public Episode getEpisodeByID(UUID episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(() -> new NotFoundException("Episode not found :: " + episodeId));
        if (episode.getRecordStatus() == RecordStatus.DELETED) {
            throw new NotFoundException("Episode not found :: " + episodeId);
        }
        return episode;
    }

    /**
     * Updates the details of an episode.
     *
     * @param episodeId The ID of the episode to be updated.
     * @param request   The updated details of the episode.
     * @return The ID of the updated episode.
     */
    public UUID updateEpisode(UUID episodeId, EpisodeRequest request) {
        Episode episode = this.getEpisodeByID(episodeId);
        if (!request.releaseDate().equals(episode.getReleaseDate())) episode.setReleaseDate(request.releaseDate());
        if (!request.title().equals(episode.getTitle())) episode.setTitle(request.title());
        if (!request.description().equals(episode.getDescription())) episode.setDescription(request.description());

        return episodeRepository.save(episode).getId();
    }
}
