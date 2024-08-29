package io.github.marianciuc.streamingservice.subscription.service.impl;

import io.github.marianciuc.streamingservice.subscription.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.subscription.entity.Resolution;
import io.github.marianciuc.streamingservice.subscription.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.subscription.mapper.ResolutionMapper;
import io.github.marianciuc.streamingservice.subscription.repository.ResolutionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResolutionServiceImplTest {

    // Mock dependencies
    ResolutionRepository resolutionRepository = mock(ResolutionRepository.class);

    // System Under Test (SUT)
    ResolutionMapper resolutionMapper = mock(ResolutionMapper.class);
    private ResolutionServiceImpl sut = new ResolutionServiceImpl(resolutionRepository, resolutionMapper);

    @Test
    public void whenResolutionNotFound_thenThrowNotFoundException() {
        UUID id = UUID.randomUUID();

        when(resolutionRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            sut.getResolution(id);
        });
    }

    @Test
    public void whenResolutionFound_thenDoesNotThrowException() {
        UUID id = UUID.randomUUID();
        Resolution resolution = new Resolution();
        resolution.setId(id);

        when(resolutionRepository.findById(id)).thenReturn(Optional.of(resolution));

        Assertions.assertDoesNotThrow(() -> {
            sut.getResolution(id);
        });
    }
    @Test
    public void createResolutionTest() {
        ResolutionDto resolutionDto = new ResolutionDto(
                UUID.randomUUID(),
                "description",
                "name"
        );
        Resolution resolution = new Resolution();
        when(resolutionMapper.fromResolutionDto(resolutionDto)).thenReturn(resolution);
        Assertions.assertDoesNotThrow(() -> {
           sut.createResolution(resolutionDto);
        });
    }

    @Test
    public void getAllResolutions_EmptyList() {
        when(resolutionRepository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> {
            List<Resolution> resolutions = sut.getAllResolutions();
            assertNotNull(resolutions);
            assertTrue(resolutions.isEmpty());
        });
    }

    @Test
    public void getAllResolutions_NonEmptyList() {
        Resolution resolution = new Resolution();
        when(resolutionRepository.findAll()).thenReturn(List.of(resolution));

        Assertions.assertDoesNotThrow(() -> {
            List<Resolution> resolutions = sut.getAllResolutions();
            assertNotNull(resolutions);
            assertEquals(1, resolutions.size());
            assertEquals(resolution, resolutions.get(0));
        });
    }
}
