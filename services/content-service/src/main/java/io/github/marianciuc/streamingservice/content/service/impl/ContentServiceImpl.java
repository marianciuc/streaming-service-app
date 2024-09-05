/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ContentServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.ContentDto;
import io.github.marianciuc.streamingservice.content.dto.PaginationResponse;
import io.github.marianciuc.streamingservice.content.entity.*;
import io.github.marianciuc.streamingservice.content.enums.ContentType;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.kafka.KafkaContentProducer;
import io.github.marianciuc.streamingservice.content.repository.ContentRepository;
import io.github.marianciuc.streamingservice.content.service.*;
import io.github.marianciuc.streamingservice.content.service.ContentService;
import io.github.marianciuc.streamingservice.content.specifications.ContentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository repository;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final TagService tagService;
    private final GenreService genreService;
    private final KafkaContentProducer kafkaContentProducer;

    @Override
    public UUID createContent(ContentDto contentDto) {
        List<Actor> actors = contentDto.actorList().stream().map(actorDto -> actorService.findActorEntityById(actorDto.id())).toList();
        List<Director> directors = contentDto.directorList().stream().map(directorDto -> directorService.findDirectorEntityById(directorDto.id())).toList();
        List<Tag> tags = contentDto.tagList().stream().map(tagDto -> tagService.findTagById(tagDto.id())).toList();
        List<Genre> genres = contentDto.genreList().stream().map(genreDto -> genreService.getGenreEntity(genreDto.id())).toList();

        Content content = Content.builder()
                .contentType(contentDto.type())
                .actors(actors)
                .ageRating(contentDto.ageRating())
                .description(contentDto.description())
                .directors(directors)
                .genres(genres)
                .posterUrl(contentDto.posterUrl())
                .tags(tags)
                .rating(0.0)
                .releaseDate(contentDto.releaseDate())
                .build();
        return repository.save(content).getId();
    }

    @Override
    public ContentDto getContent(UUID contentId) {
        return ContentDto.toContentDto(this.getContentEntity(contentId));
    }

    @Override
    public void updateContent(UUID contentId, ContentDto contentDto) {
        Content content = getContentEntity(contentId);
        if (contentDto.title() != null) content.setTitle(contentDto.title());
        if (contentDto.description() != null) content.setDescription(contentDto.description());
        if (contentDto.type() != null) content.setContentType(contentDto.type());
        if (contentDto.releaseDate() != null) content.setReleaseDate(contentDto.releaseDate());
        if (contentDto.ageRating() != null) content.setAgeRating(contentDto.ageRating());
        if (contentDto.posterUrl() != null) content.setPosterUrl(contentDto.posterUrl());
        if (contentDto.genreList() != null) {
            List<Genre> genres = contentDto.genreList().stream().map(genreDto -> genreService.getGenreEntity(genreDto.id())).toList();
            content.setGenres(genres);
        }
        if (contentDto.directorList() != null) {
            List<Director> directors = contentDto.directorList().stream().map(directorDto -> directorService.findDirectorEntityById(directorDto.id())).toList();
            content.setDirectors(directors);
        }
        if (contentDto.actorList() != null) {
            List<Actor> actors = contentDto.actorList().stream().map(actorDto -> actorService.findActorEntityById(actorDto.id())).toList();
            content.setActors(actors);
        }
        if (contentDto.tagList() != null) {
            List<Tag> tags = contentDto.tagList().stream().map(tagDto -> tagService.findTagById(tagDto.id())).toList();
            content.setTags(tags);
        }
        repository.save(content);
    }

    @Override
    public void deleteContent(UUID contentId) {
        Content content = getContentEntity(contentId);
        content.setRecordStatus(RecordStatus.DELETED);
        repository.save(content);
        kafkaContentProducer.sendDeleteContentMessage(contentId);
    }

    @Override
    public Content getContentEntity(UUID contentId) {
        return repository.findById(contentId).orElseThrow(()-> new NotFoundException("Content not found"));
    }

    @Override
    public PaginationResponse<List<ContentDto>> getAllContentByFilters(
            String title,
            UUID genreId,
            UUID directorId,
            Integer page,
            Integer pageSize,
            UUID actorId,
            String ageRating,
            String releaseDateYear,
            ContentType contentType,
            RecordStatus recordStatus
    ) {
        Specification<Content> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) spec = spec.and(ContentSpecification.titleContains(title));
        if (genreId != null) spec = spec.and(ContentSpecification.hasGenre(genreId));
        if (directorId != null) spec = spec.and(ContentSpecification.hasDirector(directorId));
        if (actorId != null) spec = spec.and(ContentSpecification.actorIdIs(actorId));
        if (ageRating != null && !ageRating.isEmpty()) spec = spec.and(ContentSpecification.ageRatingIs(ageRating));
        if (releaseDateYear != null && !releaseDateYear.isEmpty()) spec = spec.and(ContentSpecification.releaseDateYearIs(releaseDateYear));
        if (contentType != null) spec = spec.and(ContentSpecification.typeIs(contentType));
        if (recordStatus != null) spec = spec.and(ContentSpecification.recordStatusIs(recordStatus));

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Content> contentPage = repository.findAll(spec, pageRequest);

        return new PaginationResponse<>(
                contentPage.getTotalPages(),
                contentPage.getNumber(),
                contentPage.getSize(),
                contentPage.getContent().stream().map(ContentDto::toContentDto).toList()
        );
    }
}
