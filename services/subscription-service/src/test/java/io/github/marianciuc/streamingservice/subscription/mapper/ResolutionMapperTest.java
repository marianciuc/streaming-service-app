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
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Test class for ResolutionMapper
 */
public class ResolutionMapperTest {

    /**
     * Test to verify fromResolutionDto maps ResolutionDto to Resolution correctly
     */
    @Test
    public void testFromResolutionDto() {
        // Prepare data
        UUID id = UUID.randomUUID();
        String name = "testName";
        String description = "testDescription";

        ResolutionDto resolutionDto = new ResolutionDto(id, name, description);

        // Perform the mapping
        ResolutionMapper resolutionMapper = new ResolutionMapper();
        Resolution resolution = resolutionMapper.fromResolutionDto(resolutionDto);

        // Validate the mapping
        assertNotNull(resolution);
        assertEquals(id, resolution.getId());
        assertEquals(name, resolution.getName());
        assertEquals(description, resolution.getDescription());
    }
}
    /**
     * Test to verify toResolutionDto maps Resolution to ResolutionDto correctly
     */
    @Test
    public void testToResolutionDto() {
        // Prepare data
        UUID id = UUID.randomUUID();
        String name = "testName";
        String description = "testDescription";

        Resolution resolution = Resolution.builder()
                                         .id(id)
                                         .name(name)
                                         .description(description)
                                         .build();

        // Perform the mapping
        ResolutionMapper resolutionMapper = new ResolutionMapper();
        ResolutionDto resolutionDto = resolutionMapper.toResolutionDto(resolution);

        // Validate the mapping
        assertNotNull(resolutionDto);
        assertEquals(id, resolutionDto.id());
        assertEquals(name, resolutionDto.name());
        assertEquals(description, resolutionDto.description());
    }
}
