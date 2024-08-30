/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FFmpegCommandCompressingService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.services.VideoCompressingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FFmpegCompressingService implements VideoCompressingService {

    private static final String IO_EXCEPTION_MSG = "An exception occurred while interacting with the filesystem.";
    private static final String INTERRUPTED_EXCEPTION_MSG = "The compression operation was interrupted.";
    private static final String COMPRESSION_ERROR_MSG = "Error occurred during compression.";
    private static final String FFMPEG_COMMAND = "ffmpeg";
    private static final String CODEC = "libaom-av1";
    private static final String TEMP_FILE_SUFFIX = ".tmp";

    @Override
    public byte[] compressVideo(MultipartFile file, Resolution resolution) {
        File inputFile = null;
        File outputFile = null;
        try {
            inputFile = createTempFile("input", file);
            file.transferTo(inputFile);

            outputFile = createTempFile("output", file);
            return executeCompression(inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), resolution);
        } catch (IOException e) {
            throw new RuntimeException(IO_EXCEPTION_MSG, e);
        } finally {
            deleteFile(inputFile);
            deleteFile(outputFile);
        }
    }

    private File createTempFile(String prefix, MultipartFile file) throws IOException {
        return File.createTempFile(prefix + file.getName(), TEMP_FILE_SUFFIX);
    }

    private byte[] executeCompression(String inputFilePath, String outputFilePath, Resolution resolution) {
        Process process = null;
        try {
            List<String> command = createCommand(inputFilePath, outputFilePath, resolution.getHeight(), resolution.getBitrate());
            process = startProcess(command);
            if (process.waitFor() == 0) {
                return readFileAsBytes(outputFilePath);
            }
            throw new RuntimeException(COMPRESSION_ERROR_MSG);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(INTERRUPTED_EXCEPTION_MSG, e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private List<String> createCommand(String inputFile, String outputFile, int scale, int bitrate) {
        return List.of(
                FFMPEG_COMMAND, "-i", inputFile, "-vf", "scale=-1:" + scale,
                "-vcodec", CODEC, "-b:v", bitrate + "k", outputFile
        );
    }

    private Process startProcess(List<String> command) throws IOException {
        return new ProcessBuilder(command).start();
    }

    private byte[] readFileAsBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    private void deleteFile(File file) {
        if (file != null) {
            file.delete();
        }
    }
}
