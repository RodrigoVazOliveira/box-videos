package dev.rvz.boxvideos.adapters.inbound.requests.videos;

public record CreateVideoRequest(
        String title, String description, String url
) { }