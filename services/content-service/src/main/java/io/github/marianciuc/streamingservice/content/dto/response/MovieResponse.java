package io.github.marianciuc.streamingservice.content.dto.response;

import java.util.List;
import java.util.UUID;

public record MovieResponse(
        UUID id,
        UUID contentId,
        List<MediaLinkResponse> mediaLinks
) {
}
