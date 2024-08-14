package com.mv.streamingservice.content.mappers;

import com.mv.streamingservice.content.dto.request.EpisodeRequest;
import com.mv.streamingservice.content.dto.response.EpisodeResponse;
import com.mv.streamingservice.content.entity.Episode;
import com.mv.streamingservice.content.entity.MediaLink;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EpisodeMapperTest {

    @Test
    void toDto_ShouldReturnDto_WhenEntityIsProvided() {
        // Given
        MediaLinkMapper mediaLinkMapper = mock(MediaLinkMapper.class);
        MediaLink mediaLink = mock(MediaLink.class);
        when(mediaLinkMapper.toDto(mediaLink)).thenReturn(null);

        EpisodeMapper episodeMapper = new EpisodeMapper(mediaLinkMapper);
        Episode episode = Episode.builder()
                .id(UUID.randomUUID())
                .title("Test Episode")
                .releaseDate(LocalDateTime.now())
                .description("Test Description")
                .episodeNumber(1)
                .mediaLinks(Collections.singletonList(mediaLink))
                .build();

        // When
        EpisodeResponse episodeResponse = episodeMapper.toDto(episode);

        // Then
        assertNotNull(episodeResponse);
        assertEquals(episode.getId(), episodeResponse.id());
        assertEquals(episode.getTitle(), episodeResponse.title());
        assertEquals(episode.getReleaseDate(), episodeResponse.releaseDate());
        assertEquals(episode.getDescription(), episodeResponse.description());
        assertEquals(episode.getEpisodeNumber(), episodeResponse.episodeNumber());
    }

    @Test
    void toEntity_ShouldReturnEntity_WhenDtoIsProvided() {
        // Given
        EpisodeRequest episodeRequest = new EpisodeRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                1,
                "test",
                LocalDateTime.now(),
                "description"
                );
        EpisodeMapper episodeMapper = new EpisodeMapper(null);

        // When
        Episode episode = episodeMapper.toEntity(episodeRequest);

        // Then
        assertNotNull(episode);
        assertEquals(episodeRequest.id(), episode.getId());
    }
}
