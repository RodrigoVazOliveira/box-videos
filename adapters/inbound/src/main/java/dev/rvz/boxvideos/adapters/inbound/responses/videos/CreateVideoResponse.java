package dev.rvz.boxvideos.adapters.inbound.responses.videos;

public record CreateVideoResponse(
        Long id, String title, String description, String url
) {
}
