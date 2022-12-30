package dev.rvz.boxvideos.adapters.commons.requests.videos;

public record UpdatePartialRequest(String title, String description, String url, Long categoryId) {
}
