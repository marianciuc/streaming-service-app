package io.github.marianciuc.streamingservice.content.mappers;

import io.github.marianciuc.streamingservice.content.dto.request.MediaLinkRequest;
import io.github.marianciuc.streamingservice.content.dto.response.MediaLinkResponse;
import io.github.marianciuc.streamingservice.content.entity.MediaLink;
import org.springframework.stereotype.Service;

@Service
public class MediaLinkMapper implements GenericMapper<MediaLink, MediaLinkResponse, MediaLinkRequest>{
    @Override
    public MediaLinkResponse toDto(MediaLink entity) {
        return new MediaLinkResponse(
                entity.getId(),
                entity.getResolution(),
                entity.getLink()
        );
    }

    @Override
    public MediaLink toEntity(MediaLinkRequest dto) {
        return MediaLink.builder()
                .id(dto.id())
                .link(dto.link())
                .resolution(dto.resolution())
                .build();
    }
}
