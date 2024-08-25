package io.github.marianciuc.streamingservice.media.entity;

import lombok.Getter;


/**
 * An enumeration representing different resolutions of media files.
 */
@Getter
public enum Resolution {
    SD(480, 2000, 0),
    HD(720, 4000, 1),
    FHD( 1080, 15000, 2),
    QHD( 1600, 35000, 3);

    private final int height;
    private final int bitrate;
    private final int order;

    Resolution(int height, int bitrate, int order) {
        this.height = height;
        this.bitrate = bitrate;
        this.order = order;
    }

    public static Resolution getResolutionByOrder(int order) {
        for (Resolution resolution : values()) {
            if (resolution.order == order) {
                return resolution;
            }
        }
        throw new IllegalArgumentException("No resolution found for order: " + order);
    }
}