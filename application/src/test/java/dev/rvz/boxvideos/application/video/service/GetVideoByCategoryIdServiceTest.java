package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import dev.rvz.boxvideos.port.out.video.GetVideoByCategoryIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

final class GetVideoByCategoryIdServiceTest {


    @Test
    void getVideoByCategoryWithSuccess() {
        GetCategoryByIdPortIn getCategoryByIdPortIn = id -> {
            final Category category = new Category(1L, "LIVRE", "WHITE");
            return category;
        };


        GetVideoByCategoryIdPortOut getVideoByCategoryIdPortOut = id -> {
            final Category category = new Category(1L, "LIVRE", "WHITE");
            final Video video = new Video(1L, "titulo 1", "Descriacao", "http://localhost", category);

            return List.of(video);
        };

        GetVideoByCategoryIdService getVideoByCategoryIdService = new GetVideoByCategoryIdService(getCategoryByIdPortIn, getVideoByCategoryIdPortOut);
        Iterable<Video> result = getVideoByCategoryIdService.run(1L);

        List<Video> videos = new ArrayList<>();
        result.forEach(videos::add);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, videos.size());
    }

    @Test
    void getVideoByCategoryNotFound() {
        GetCategoryByIdPortIn getCategoryByIdPortIn = id -> {
            throw new CategoryNotFoundException("categoria com id %d não existe.".formatted(id));
        };
        GetVideoByCategoryIdPortOut getVideoByCategoryIdPortOut = id -> null;

        GetVideoByCategoryIdService getVideoByCategoryIdService = new GetVideoByCategoryIdService(getCategoryByIdPortIn, getVideoByCategoryIdPortOut);
        CategoryNotFoundException resultException = Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            getVideoByCategoryIdService.run(1L);
        });

        Assertions.assertEquals("categoria com id 1 não existe.",
                resultException.getMessage());
    }


}