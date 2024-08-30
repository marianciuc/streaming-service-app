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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FFmpegCompressingService implements VideoCompressingService {

    private static final String IO_EXCEPTION_MSG = "An exception occurred while interacting with the filesystem.";
    private static final String INTERRUPTED_EXCEPTION_MSG = "The compression operation was interrupted.";
    private static final String COMPRESSION_ERROR_MSG = "Error occurred during compression.";
    private static final String FFMPEG_COMMAND = "ffmpeg";
    private static final String CODEC = "libaom-av1";

    @Override
    public byte[] compressVideo(MultipartFile file, Resolution resolution) {
        File inputFile = null;
        File outputFile = null;

        try {
            inputFile = File.createTempFile("input" + file.getName(), ".tmp");
            file.transferTo(inputFile);

            outputFile = File.createTempFile("output" + file.getName(), ".tmp");
            return this.exactCompress(inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), resolution.getHeight(), resolution.getBitrate());
        } catch (IOException e) {
            throw new RuntimeException(IO_EXCEPTION_MSG, e);
        } finally {
            Objects.requireNonNull(inputFile).delete();
            Objects.requireNonNull(outputFile).delete();
        }
    }

    private byte[] exactCompress(String inputFile, String outputFile, Integer scale, Integer bitrate) {
        try {
            List<String> command = createCommand(inputFile, outputFile, scale, bitrate);
            Process process = startProcess(command);
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return readFileAsBytes(outputFile);
            }
            throw new RuntimeException(COMPRESSION_ERROR_MSG);
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException(INTERRUPTED_EXCEPTION_MSG, exception);
        }
    }

    private Process startProcess(List<String> command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        return processBuilder.start();
    }

    private byte[] readFileAsBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    private List<String> createCommand(String inputFile, String outputFile, Integer scale, Integer bitrate) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_COMMAND);
        command.add("-i");
        command.add(inputFile);
        command.add("-vf");
        command.add("scale=-1:" + scale);
        command.add("-vcodec");
        command.add(CODEC);
        command.add("-b:v");
        command.add(bitrate + "k");
        command.add(outputFile);
        return command;
    }
}
