/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FFmpegJavaCVService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.exceptions.CompressingException;
import io.github.marianciuc.streamingservice.media.services.VideoCompressingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Service that handles video compression using FFmpeg and JavaCV.
 * Implements the VideoCompressingService interface.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FFmpegJavaCVService implements VideoCompressingService {

    private static final String TEMP_FILE_SUFFIX = ".mp4";
    private static final String IO_EXCEPTION_MSG = "An exception occurred while interacting with the filesystem.";
    private static final String FRAME_GRABBING_ERROR_MSG = "Error during frame grabbing or recording.";
    private static final String PREFIX_INPUT_FILE = "input";
    private static final String PREFIX_OUTPUT_FILE = "output";

    private static final int VIDEO_CODEC = avcodec.AV_CODEC_ID_H264;
    private static final int AUDIO_CODEC = avcodec.AV_CODEC_ID_AAC;
    private static final int PIXEL_FORMAT = avutil.AV_PIX_FMT_YUV420P;
    private static final String FORMAT = "mp4";

    /**
     * Compresses the given video file to the specified resolution.
     *
     * @param file       the video file to be compressed
     * @param resolution the desired resolution to which the video should be compressed
     * @return a byte array containing the compressed video
     */
    @Override
    public byte[] compressVideo(MultipartFile file, Resolution resolution) {
        File inputFile = null;
        File outputFile = null;
        try {
            log.info("MultipartFile name: {}", file.getOriginalFilename());
            log.info("MultipartFile size: {}", file.getSize());

            inputFile = createTempFile(PREFIX_INPUT_FILE, file);
            Files.copy(file.getInputStream(), Path.of(inputFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);

            outputFile = createTempFile(PREFIX_OUTPUT_FILE, file);
            executeCompression(inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), resolution);
            return Files.readAllBytes(outputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(IO_EXCEPTION_MSG, e);
        } finally {
            deleteFile(inputFile);
            deleteFile(outputFile);
        }
    }


    /**
     * Executes the video compression process using the specified input file path,
     * output file path, and target resolution.
     *
     * @param inputFilePath  the path to the input video file to be compressed
     * @param outputFilePath the path where the compressed video file will be saved
     * @param resolution     the target resolution to which the video should be compressed
     */
    private void executeCompression(String inputFilePath, String outputFilePath, Resolution resolution) {
        log.info("Starting video compression...");
        log.info("Input file: {}", inputFilePath);
        log.info("Output file: {}", outputFilePath);

        FFmpegLogCallback.set();

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath);
             FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
                     outputFilePath,
                     resolution.getWidth(),
                     resolution.getHeight()
             )
        ) {
            grabber.setFormat(FORMAT);
            grabber.start();

            recorder.setVideoCodec(VIDEO_CODEC);
            recorder.setPixelFormat(PIXEL_FORMAT);
            recorder.setAudioCodec(AUDIO_CODEC);
            recorder.setSampleRate(grabber.getSampleRate());
            recorder.setAudioChannels(grabber.getAudioChannels());
            recorder.setFormat(FORMAT);
            recorder.setFrameRate(grabber.getFrameRate());
            recorder.setVideoBitrate(resolution.getBitrate());
            recorder.start();

            while (true) {
                Frame frame = grabber.grab();
                if (frame == null) {
                    break;
                }
                recorder.record(frame);
            }

            recorder.stop();
            grabber.stop();
        } catch (FrameGrabber.Exception | FrameRecorder.Exception e) {
            log.error(FRAME_GRABBING_ERROR_MSG, e);
            throw new CompressingException(FRAME_GRABBING_ERROR_MSG);
        }
    }

    private File createTempFile(String prefix, MultipartFile file) throws IOException {
        return File.createTempFile(prefix + file.getName(), TEMP_FILE_SUFFIX).toPath().toFile();
    }

    private void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (!file.delete()) {
                log.warn("Failed to delete temporary file: {}", file.getAbsolutePath());
            }
        }
    }
}
