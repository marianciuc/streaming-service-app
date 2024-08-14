package com.mv.streamingservice.content.service;


import com.mv.streamingservice.content.entity.Season;
import com.mv.streamingservice.content.exceptions.NotFoundException;
import com.mv.streamingservice.content.mappers.SeasonMapper;
import com.mv.streamingservice.content.repository.SeasonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class SeasonServiceTest {
    SeasonRepository seasonRepository = Mockito.mock(SeasonRepository.class);
    SeasonMapper seasonMapper = Mockito.mock(SeasonMapper.class);
    ContentService contentService = Mockito.mock(ContentService.class);
    SeasonService seasonService = new SeasonService(seasonRepository, seasonMapper, contentService);

    @Test
    public void testGetSeasonById() {
        UUID seasonId = UUID.randomUUID();
        Season expectedSeason = Mockito.mock(Season.class);
        Mockito.when(seasonRepository.findById(seasonId)).thenReturn(Optional.of(expectedSeason));
        Mockito.when(expectedSeason.isRecordStatusDeleted()).thenReturn(false);
        Season actualSeason = seasonService.getSeasonById(seasonId);
        Assertions.assertEquals(expectedSeason, actualSeason);
    }

    @Test
    public void testGetSeasonByIdNotFound() {
        UUID seasonId = UUID.randomUUID();
        Mockito.when(seasonRepository.findById(seasonId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> seasonService.getSeasonById(seasonId));
    }

    @Test
    public void testGetSeasonByIdDeleted() {
        UUID seasonId = UUID.randomUUID();
        Season deletedSeason = Mockito.mock(Season.class);
        Mockito.when(seasonRepository.findById(seasonId)).thenReturn(Optional.of(deletedSeason));
        Mockito.when(deletedSeason.isRecordStatusDeleted()).thenReturn(true);
        Assertions.assertThrows(NotFoundException.class, () -> seasonService.getSeasonById(seasonId));
    }
}