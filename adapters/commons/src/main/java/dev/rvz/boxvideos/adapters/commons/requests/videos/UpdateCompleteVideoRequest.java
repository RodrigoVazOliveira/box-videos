package dev.rvz.boxvideos.adapters.commons.requests.videos;

public record UpdateCompleteVideoRequest(String title, String description, String url, Long categoryId) {
}
