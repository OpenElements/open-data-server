package com.openelements.opendata.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;

@Schema(enumAsRef = true, description = "Allowed data types")
public enum DataType {
    PNG(MediaType.IMAGE_PNG),
    SVG(MediaType.valueOf("image/svg+xml")),
    UNKNOWN(MediaType.APPLICATION_OCTET_STREAM);

    private final MediaType mediaType;

    DataType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @JsonCreator
    public static DataType fromString(final String value) {
        return switch (value.toLowerCase()) {
            case "png" -> PNG;
            case "svg" -> SVG;
            default -> UNKNOWN;
        };
    }
}
