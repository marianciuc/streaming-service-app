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

package io.github.marianciuc.streamingservice.subscription.mapper;

import io.github.marianciuc.streamingservice.subscription.entity.Resolution;
import io.github.marianciuc.streamingservice.subscription.dto.ResolutionDto;
import org.springframework.stereotype.Component;

/**
 * Class that maps ResolutionDto objects to Resolution objects and vice versa.
 */
@Component
public class ResolutionMapper {

    /**
     * Maps a {@link ResolutionDto} object to a {@link Resolution} object.
     *
     * @param request the {@link ResolutionDto} to be mapped
     * @return the mapped {@link Resolution} object
     */
    public Resolution fromResolutionDto(ResolutionDto request) {
        return Resolution.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .build();
    }

    /**
     * Converts a {@link Resolution} object to a {@link ResolutionDto} object.
     *
     * @param resolution the {@link Resolution} to be converted
     * @return the converted {@link ResolutionDto}
     */
    public ResolutionDto toResolutionDto(Resolution resolution) {
        return new ResolutionDto(
                resolution.getId(),
                resolution.getDescription(),
                resolution.getName()
        );
    }
}
