package com.mv.streamingservice.content.mappers;

import com.mv.streamingservice.content.dto.response.EpisodeResponse;
import com.mv.streamingservice.content.dto.request.EpisodeRequest;
import com.mv.streamingservice.content.entity.Episode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpisodeMapper implements GenericMapper<Episode, EpisodeResponse, EpisodeRequest>{

    private final MediaLinkMapper mediaLinkMapper;

    @Override
    public EpisodeResponse toDto(Episode entity) {
        return new EpisodeResponse(
                entity.getId(),
                entity.getEpisodeNumber(),
                entity.getTitle(),
                entity.getReleaseDate(),
                entity.getDescription(),
                entity.getMediaLinks().stream().map(mediaLinkMapper::toDto).collect(Collectors.toList())
        );
    }

    @Override
    public Episode toEntity(EpisodeRequest dto) {
        return Episode.builder()
                .id(dto.id())
                .title(dto.title())
                .releaseDate(dto.releaseDate())
                .description(dto.description())
                .episodeNumber(dto.episodeNumber())
                .build();
    }
}
