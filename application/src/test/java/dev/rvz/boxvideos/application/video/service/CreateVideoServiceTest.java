package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import dev.rvz.boxvideos.port.out.video.CreateVideoPortout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateVideoServiceTest {

    private final CreateVideoPortout createVideoPortout = video -> new Video(1L, video.title(), video.description(), video.url(), video.category());


    @Test
    void test_video_return_with_success() {
        GetCategoryByIdPortIn getCategoryByIdPortIn = new GetCategoryByIdPortIn() {
            @Override
            public Category getCategoryById(Long id) {
                return new Category(id, "LIVRE", "WHITE");
            }
        };
        
        Video requestSaveVideo = new Video(
                null,
                "Testes 1",
                "Descrição",
                "http://meuvideo.comb.br",
                new Category(1L, "LIVRE", "WHITE"));
        CreateVideoService createVideoService = new CreateVideoService(createVideoPortout, getCategoryByIdPortIn);

        Video result = createVideoService.execute(requestSaveVideo);

        Assertions.assertEquals(requestSaveVideo.title(), result.title());
        Assertions.assertEquals(requestSaveVideo.description(), result.description());
        Assertions.assertEquals(requestSaveVideo.url(), result.url());
        Assertions.assertNotNull(result.category());
        Assertions.assertEquals(requestSaveVideo.category().id(), result.category().id());
        Assertions.assertEquals(1L, result.id());
    }

}