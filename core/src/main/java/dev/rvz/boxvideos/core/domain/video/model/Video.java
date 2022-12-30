package dev.rvz.boxvideos.core.domain.video.model;

import dev.rvz.boxvideos.core.domain.category.model.Category;

public record Video(Long id, String title, String description, String url, Category category) {
}
