package com.mv.streamingservice.content.mappers;

import com.mv.streamingservice.content.dto.request.SeasonRequest;
import com.mv.streamingservice.content.dto.response.SeasonResponse;
import com.mv.streamingservice.content.entity.Season;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeasonMapper implements GenericMapper<Season, SeasonResponse, SeasonRequest> {

    private final EpisodeMapper episodeMapper;

    @Override
    public SeasonResponse toDto(Season entity) {
        return new SeasonResponse(
                entity.getId(),
                entity.getContent().getId(),
                entity.getSeasonNumber(),
                entity.getSeasonTitle(),
                entity.getSeasonReleaseDate(),
                entity.getEpisodes().stream().map(episodeMapper::toDto).collect(Collectors.toList())
        );
    }

    @Override
    public Season toEntity(SeasonRequest dto) {
        return Season.builder()
                .id(dto.id())
                .seasonNumber(dto.seasonNumber())
                .seasonTitle(dto.seasonTitle())
                .seasonReleaseDate(dto.releaseDate())
                .build();
    }
}
