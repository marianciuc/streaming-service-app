package com.mv.streamingservice.content.service;

import com.mv.streamingservice.content.dto.request.GenreRequest;
import com.mv.streamingservice.content.entity.Genre;
import com.mv.streamingservice.content.mappers.PageMapper;
import com.mv.streamingservice.content.repository.GenreRepository;
import com.mv.streamingservice.content.mappers.GenreMapper;
import com.mv.streamingservice.content.enums.RecordStatus;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
public class GenreServiceTest {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final PageMapper pageMapper;
    private final GenreService genreService;

    public GenreServiceTest() {
        this.genreRepository = Mockito.mock(GenreRepository.class);
        this.genreMapper = Mockito.mock(GenreMapper.class);
        this.pageMapper = Mockito.mock(PageMapper.class);
        this.genreService = new GenreService(genreRepository, genreMapper, pageMapper);
    }

    /**
     * Tests the createGenre method in the GenreService class.
     */
    @Test
    public void createGenreTest() {
        Mockito.verify(genreRepository).save(any(Genre.class));
        Mockito.verify(genreMapper).toEntity(any(GenreRequest.class));

        GenreRequest genreRequest = new GenreRequest(UUID.randomUUID(), "Test name", "Test description");
        
        // When
        UUID genreId = genreService.createGenre(genreRequest);
        
        // Then
        Genre genre = genreService.findGenreEntityById(genreId);
        
        assertThat(genre.getId()).isEqualTo(genreId);
        assertThat(genre.getName()).isEqualTo(genreRequest.name());
        assertThat(genre.getDescription()).isEqualTo(genreRequest.description());
        assertThat(genre.getRecordStatus()).isEqualTo(RecordStatus.ACTIVE);
    }
}