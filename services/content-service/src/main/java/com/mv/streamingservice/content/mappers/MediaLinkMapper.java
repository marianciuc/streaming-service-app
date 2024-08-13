package com.mv.streamingservice.content.mappers;

import com.mv.streamingservice.content.dto.request.MediaLinkRequest;
import com.mv.streamingservice.content.dto.response.MediaLinkResponse;
import com.mv.streamingservice.content.entity.MediaLink;
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
        return null;
    }
}
