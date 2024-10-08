/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FFmpegJavaCVService.java
 *
 */
package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.exceptions.CompressingException;
import io.github.marianciuc.streamingservice.media.exceptions.VideoStorageUploadException;
import io.github.marianciuc.streamingservice.media.services.PlaylistService;
import io.github.marianciuc.streamingservice.media.services.VideoCompressingService;
import io.github.marianciuc.streamingservice.media.services.VideoStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FFmpegJavaCVService implements VideoCompressingService {

    private static final String TEMP_FILE_SUFFIX = ".mp4";
    private static final String IO_EXCEPTION_MSG = "An exception occurred while interacting with the filesystem.";
    private static final String FRAME_GRABBING_ERROR_MSG = "Error during frame grabbing or recording.";
    private static final String PREFIX_INPUT_FILE = "input";
    private static final int VIDEO_CODEC = avcodec.AV_CODEC_ID_H264;
    private static final int AUDIO_CODEC = avcodec.AV_CODEC_ID_AAC;
    private static final int PIXEL_FORMAT = avutil.AV_PIX_FMT_YUV420P;
    private static final String OUTPUT_FORMAT = "hls";
    private static final int CHUNK_DURATION_IN_SECONDS = 10;
    private static final String HLS_SEGMENT_FILENAME = "segment%d.ts";
    private static final String CONTENT_TYPE_VIDEO_MP2T = "video/mp2t";

    private final VideoStorageService videoStorageService;
    private final PlaylistService playlistService;

    @Override
    public String compressVideoAndUploadToStorage(ResolutionDto resolution, UUID id) throws CompressingException {
        File inputFile = null;

        try {
            inputFile = createTempFile();
            Files.copy(videoStorageService.assembleVideoTemporaryVideoFile(id), Path.of(inputFile.getAbsolutePath()),
                    StandardCopyOption.REPLACE_EXISTING);
            return executeCompression(inputFile.getAbsolutePath(), resolution, id);
        } catch (IOException e) {
            throw new CompressingException(IO_EXCEPTION_MSG, e);
        } finally {
            deleteFile(inputFile);
        }
    }

    private String executeCompression(String inputFilePath, ResolutionDto resolution, UUID id) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            grabber.start();
            int frameRate = (int) grabber.getFrameRate();
            int totalFramesPerChunk = frameRate * CHUNK_DURATION_IN_SECONDS;

            Frame frame;
            FFmpegFrameRecorder recorder = null;
            StringBuilder resolutionPlaylist = playlistService.generateResolutionPlaylist();
            int chunkCounter = 0;

            while ((frame = grabber.grab()) != null) {
                if (recorder == null || recorder.getFrameNumber() >= totalFramesPerChunk) {
                    if (recorder != null) {
                        recorder.stop();
                        uploadChunkAndResetStream(outputStream, resolution, id, resolutionPlaylist, chunkCounter);
                        chunkCounter++;
                    }
                    recorder = initializeRecorder(outputStream, resolution, frameRate, grabber);
                }
                recorder.record(frame);
            }

            if (recorder != null) {
                recorder.stop();
                uploadChunkAndResetStream(outputStream, resolution, id, resolutionPlaylist, chunkCounter);
            }

            grabber.stop();
            return playlistService.buildResolutionPlaylist(id, resolutionPlaylist, resolution);
        } catch (Exception e) {
            log.error(FRAME_GRABBING_ERROR_MSG, e);
            throw new CompressingException(FRAME_GRABBING_ERROR_MSG, e);
        }
    }

    private FFmpegFrameRecorder initializeRecorder(ByteArrayOutputStream outputStream, ResolutionDto resolution, int frameRate, FFmpegFrameGrabber grabber) throws Exception {
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputStream, resolution.width(), resolution.height());

        setupRecorder(recorder, frameRate, resolution.bitrate(), grabber);
        recorder.start();

        return recorder;
    }

    private void setupRecorder(FFmpegFrameRecorder recorder, int frameRate, int bitrate, FFmpegFrameGrabber grabber) {
        recorder.setVideoCodec(VIDEO_CODEC);
        recorder.setAudioCodec(AUDIO_CODEC);
        recorder.setPixelFormat(PIXEL_FORMAT);
        recorder.setFormat(OUTPUT_FORMAT);
        recorder.setFrameRate(frameRate);
        recorder.setSampleRate(grabber.getSampleRate());
        recorder.setVideoBitrate(bitrate);
        recorder.setOption("hls_list_size", "0");
        recorder.setOption("hls_time", String.valueOf(CHUNK_DURATION_IN_SECONDS));
        recorder.setOption("hls_segment_filename", HLS_SEGMENT_FILENAME);
    }

    private void uploadChunkAndResetStream(ByteArrayOutputStream outputStream, ResolutionDto resolution, UUID fileId,
                                           StringBuilder playlist, int chunkNumber) throws VideoStorageUploadException {
        String objectName = videoStorageService.uploadVideoSegment(outputStream, fileId, resolution, chunkNumber, CONTENT_TYPE_VIDEO_MP2T);
        playlistService.appendResolutionPlaylist(objectName, playlist, CHUNK_DURATION_IN_SECONDS);
        outputStream.reset();
    }

    private File createTempFile() throws IOException {
        return File.createTempFile(FFmpegJavaCVService.PREFIX_INPUT_FILE + "input-temp-file", TEMP_FILE_SUFFIX).toPath().toFile();
    }

    private void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (!file.delete()) {
                log.warn("Failed to delete temporary file: {}", file.getAbsolutePath());
            }
        }
    }
}