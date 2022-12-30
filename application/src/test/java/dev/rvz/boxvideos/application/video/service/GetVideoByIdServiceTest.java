package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.GetVideoByIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetVideoByIdServiceTest {

    @Test
    void test_get_video_by_id_with_success() {
        GetVideoByIdPortOut getVideoByIdPortOut = new GetVideoByIdPortOut() {


            @Override
            public Video execute(Long id) {
                return new Video(
                        1L,
                        "",
                        "",
                        "",
                        new Category(1L, "", ""));
            }

            @Override
            public Boolean notExistsVideoById(Long id) {
                return false;
            }
        };
        GetVideoByIdService getVideoByIdService = new GetVideoByIdService(getVideoByIdPortOut);
        Video result = getVideoByIdService.execute(1L);

        Assertions.assertEquals(1L, result.id());
    }

    @Test
    void test_get_video_by_id_without_success() {
        GetVideoByIdPortOut getVideoByIdPortOut = new GetVideoByIdPortOut() {


            @Override
            public Video execute(Long id) {
                return null;
            }

            @Override
            public Boolean notExistsVideoById(Long id) {
                return true;
            }
        };
        GetVideoByIdService getVideoByIdService = new GetVideoByIdService(getVideoByIdPortOut);
        VideoNotFoundException resultException = Assertions.assertThrows(VideoNotFoundException.class, () -> getVideoByIdService.execute(1L));

        Assertions.assertEquals("Não existe vídeo com id 1", resultException.getMessage());
    }
}