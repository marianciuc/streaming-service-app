/*
 * Copyright (c) 2024 Vladimir Marianciuc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */

package io.github.marianciuc.streamingservice.subscription.service.impl;

import io.github.marianciuc.streamingservice.subscription.entity.Resolution;
import io.github.marianciuc.streamingservice.subscription.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.subscription.mapper.ResolutionMapper;
import io.github.marianciuc.streamingservice.subscription.repository.ResolutionRepository;
import io.github.marianciuc.streamingservice.subscription.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.subscription.service.ResolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService {

    private static final String NOT_FOUND_EXCEPTION_MSG_PATTERN = "No resolution found with id: %s";

    private final ResolutionRepository resolutionRepository;
    private final ResolutionMapper resolutionMapper;


    @Override
    public Resolution getResolution(UUID id) {
        return resolutionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MSG_PATTERN, id)));
    }

    @Override
    public UUID createResolution(ResolutionDto resolution) {
        return resolutionRepository.save(resolutionMapper.fromResolutionDto(resolution)).getId();
    }

    @Override
    public List<Resolution> getAllResolutions() {
        return resolutionRepository.findAll();
    }

    @Override
    public void deleteResolution(UUID id) {
        resolutionRepository.deleteById(id);
        log.info("Deleted resolution with ID {}", id);
    }
}
