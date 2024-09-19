/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ImageStorageServiceTest.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.exceptions.ImageUploadException;
import io.github.marianciuc.streamingservice.media.services.impl.MinioImageStorageService;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageStorageServiceTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private MinioImageStorageService imageStorageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void upload_ShouldReturnFileName_ForValidFile() throws Exception {
        String originalFileName = "test.jpg";
        String generatedFileName = UUID.randomUUID() + "_" + originalFileName;

        when(file.getOriginalFilename()).thenReturn(originalFileName);
        when(file.getContentType()).thenReturn("image/jpeg");
        InputStream inputStream = mock(InputStream.class);
        when(file.getInputStream()).thenReturn(inputStream);
        when(inputStream.available()).thenReturn(123);

        String returnedFileName = imageStorageService.upload(file);

        assertNotNull(returnedFileName);
        assertTrue(returnedFileName.contains(originalFileName));
        verify(minioClient, times(1)).putObject(any(PutObjectArgs.class));
    }

    @Test
    void upload_ShouldThrowException_ForMinioException() throws Exception {
        String originalFileName = "test.jpg";
        when(file.getOriginalFilename()).thenReturn(originalFileName);
        when(file.getContentType()).thenReturn("image/jpeg");
        InputStream inputStream = mock(InputStream.class);
        when(file.getInputStream()).thenReturn(inputStream);

        doThrow(MinioException.class).when(minioClient).putObject(any(PutObjectArgs.class));

        assertThrows(ImageUploadException.class, () -> imageStorageService.upload(file));
    }

    @Test
    void find_ShouldReturnInputStream_ForValidFileName() throws Exception {
        String fileName = "test.jpg";
        GetObjectResponse inputStream = mock(GetObjectResponse.class);

        when(minioClient.getObject(any())).thenReturn(inputStream);

        InputStream returnedStream = imageStorageService.find(fileName);

        assertNotNull(returnedStream);
        verify(minioClient, times(1)).getObject(any());
    }

    @Test
    void delete_ShouldSucceed_ForValidFileName() throws Exception {
        String fileName = "test.jpg";

        imageStorageService.delete(fileName);

        verify(minioClient, times(1)).removeObject(any());
    }
}