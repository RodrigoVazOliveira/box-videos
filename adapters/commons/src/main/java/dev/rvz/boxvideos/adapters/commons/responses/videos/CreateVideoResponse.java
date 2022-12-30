package dev.rvz.boxvideos.adapters.commons.responses.videos;

import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;

public record CreateVideoResponse(Long id, String title, String description, String url,
                                  CategoryResponse categoryResponse) {
}
