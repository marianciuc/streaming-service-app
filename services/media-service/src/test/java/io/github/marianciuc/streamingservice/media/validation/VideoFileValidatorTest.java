/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoFileValidatorTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.validation;

import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VideoFileValidatorTest {

    private final VideoFileValidator videoFileValidator = new VideoFileValidator();

    @Test
    void isValid_WhenNullFile_ShouldReturnFalse() {
        boolean result = videoFileValidator.isValid(null, null);
        assertFalse(result);
    }

    @Test
    void isValid_WhenEmptyFile_ShouldReturnFalse() {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(multipartFile.isEmpty()).thenReturn(true);
        boolean result = videoFileValidator.isValid(multipartFile, null);
        assertFalse(result);
    }

    @Test
    void isValid_WhenIOExceptionThrown_ShouldReturnFalse() throws IOException {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(multipartFile.isEmpty()).thenReturn(false);
        Mockito.when(multipartFile.getInputStream()).thenThrow(IOException.class);
        boolean result = videoFileValidator.isValid(multipartFile, null);
        assertFalse(result);
    }

    @Test
    void isValid_WhenInputStreamInvalidMimeType_ShouldReturnFalse() throws IOException {
        MultipartFile multipartFile = mockMultipartFile("application/pdf");
        boolean result = videoFileValidator.isValid(multipartFile, null);
        assertFalse(result);
    }

    @Test
    void isValid_WhenInputStreamValidMimeType_ShouldReturnTrue() throws IOException {
        MultipartFile multipartFile = mockMultipartFile("video/mp4");
        boolean result = videoFileValidator.isValid(multipartFile, null);
        assertTrue(result);
    }

    private MultipartFile mockMultipartFile(String mimeType) throws IOException {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputStream inputStream = Mockito.mock(InputStream.class);
        Mockito.when(multipartFile.isEmpty()).thenReturn(false);
        Mockito.when(multipartFile.getInputStream()).thenReturn(inputStream);
        Tika tika = Mockito.mock(Tika.class);
        Mockito.when(tika.detect(inputStream)).thenReturn(mimeType);
        ReflectionTestUtils.setField(videoFileValidator, "tika", tika);
        return multipartFile;
    }
}