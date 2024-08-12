package com.mv.streamingservice.content.enums;

import lombok.Getter;


/**
 * An enumeration representing different resolutions of media files.
 */
@Getter
public enum Resolution {
    SD(480), HD(720), FHD(1080), UHD(2560);

    private final int resolution;

    Resolution(int resolution) {
        this.resolution = resolution;
    }

}