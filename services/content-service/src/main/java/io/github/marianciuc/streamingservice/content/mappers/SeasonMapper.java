package io.github.marianciuc.streamingservice.content.mappers;

import io.github.marianciuc.streamingservice.content.dto.request.SeasonRequest;
import io.github.marianciuc.streamingservice.content.dto.response.SeasonResponse;
import io.github.marianciuc.streamingservice.content.entity.Season;
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
