/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ImageFileValidatorTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageFileValidatorTest {

    @Mock
    ConstraintValidatorContext context;

    @Test
    public void testIsValid_whenFileIsNull_shouldReturnFalse() {
        ImageFileValidator validator = new ImageFileValidator();
        assertFalse(validator.isValid(null, context));
    }

    @Test
    public void testIsValid_whenFileIsEmpty_shouldReturnFalse() {
        ImageFileValidator validator = new ImageFileValidator();
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        assertFalse(validator.isValid(file, context));
    }

    @Test
    public void testIsValid_whenFileIsNotAnImage_shouldReturnFalse() throws IOException {
        ImageFileValidator validator = new ImageFileValidator();
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{}));
        assertFalse(validator.isValid(file, context));
    }

    @Test
    public void testIsValid_whenFileIsAnImage_shouldReturnTrue() throws IOException {
        ImageFileValidator validator = new ImageFileValidator();
        MultipartFile file = new MockMultipartFile(
                "file.jpg",
                "file.jpg",
                "image/jpeg",
                createImageInputStream());
        assertTrue(validator.isValid(file, context));
    }

    private InputStream createImageInputStream() throws IOException {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", os);
        return new ByteArrayInputStream(os.toByteArray());
    }
}