package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.controllers.ResourceResponse;
import io.github.marianciuc.streamingservice.media.entity.Media;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.entity.RecordStatus;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.media.repository.MediaRepository;
import io.github.marianciuc.streamingservice.media.repository.VideoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MediaService {
    private static final String IO_EXCEPTION_MSG = "An exception occurred while interacting with the filesystem.";
    private static final String INTERRUPTED_EXCEPTION_MSG = "The compression operation was interrupted.";
    private static final String COMPRESSION_ERROR_MSG = "Error occurred during compression.";
    private static final String FFMPEG_COMMAND = "ffmpeg";
    private static final String CODEC = "libaom-av1";

    private final MediaRepository mediaRepository;
    private final VideoRepository videoRepository;

    public UUID uploadPicture(MultipartFile file) {
        try {
            Media media = Media.builder()
                    .recordStatus(RecordStatus.ACTIVE)
                    .data(file.getBytes())
                    .contentType(file.getContentType())
                    .build();
            return mediaRepository.save(media).getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getPicture(UUID id) {
        return mediaRepository.findById(id).orElseThrow(() -> new NotFoundException("Media not found")).getData();
    }

    public void uploadVideo(boolean isEpisode, UUID mediaId, MultipartFile file, Resolution sourceResolution) {
        for (int i = sourceResolution.getOrder() - 1; i >= 0; i--) {
            Resolution resolution = Resolution.getResolutionByOrder(i);
            byte[] compressedVideo = compressVideo(file, resolution);
            Video video = createVideoObject(mediaId, isEpisode, compressedVideo, resolution, null);
            video = this.mediaRepository.save(video);
            // TODO SEND TOPIC
        }
        try {
            Video video = createVideoObject(mediaId, isEpisode, file.getBytes(), sourceResolution, RecordStatus.ACTIVE);
            video = this.mediaRepository.save(video);

            // TODO send topic
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Video createVideoObject(UUID mediaId, boolean isMovie, byte[] data, Resolution resolution, RecordStatus recordStatus) {
        return Video.builder()
                .contentMediaId(mediaId)
                .isMovie(isMovie)
                .data(data)
                .resolution(resolution)
                .recordStatus(recordStatus)
                .build();
    }

    private byte[] compressVideo(MultipartFile file, Resolution resolution) {
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

    public ResourceResponse getVideoResource(UUID videoId, HttpServletRequest request) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new NotFoundException("Video not found"));

        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        long fileLength = video.getData().length;
        long rangeStart = 0;
        long rangeEnd = fileLength - 1;

        if (rangeHeader != null) {
            String[] ranges = rangeHeader.replace("bytes=", "").split("-");
            if (ranges.length > 1) {
                rangeStart = Long.parseLong(ranges[0]);
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeStart = Long.parseLong(ranges[0]);
            }
        }

        long rangeLength = rangeEnd - rangeStart + 1;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(video.getData());
        inputStream.skip(rangeStart);

        return new ResourceResponse(
                rangeHeader != null ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK,
                "video/mp4",
                String.valueOf(rangeLength),
                rangeStart,
                rangeEnd,
                fileLength,
                new InputStreamResource(inputStream)
        );
    }
}
