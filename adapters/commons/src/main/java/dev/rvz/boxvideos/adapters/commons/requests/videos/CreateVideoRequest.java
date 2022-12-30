package dev.rvz.boxvideos.adapters.commons.requests.videos;

public record CreateVideoRequest(String title, String description, String url, Long categoryId) {
}