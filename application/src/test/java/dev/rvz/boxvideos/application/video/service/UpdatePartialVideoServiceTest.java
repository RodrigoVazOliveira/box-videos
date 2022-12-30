package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.UpdatePartialVideoPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UpdatePartialVideoServiceTest {

    @Test
    void video_not_exists_then_generate_exception() {
        Video video = new Video(1L, "", "", "", new Category(1L, "", ""));
        UpdatePartialVideoPortOut updatePartialVideoPortOut = new UpdatePartialVideoPortOut() {
            @Override
            public Video updateAlreadyExists(Video video) {
                return null;
            }

            @Override
            public Boolean notExistsVideoById(Long id) {
                return true;
            }
        };
        UpdatePartialVideoService updatePartialVideoService = new UpdatePartialVideoService(updatePartialVideoPortOut);

        VideoNotFoundException resultException = Assertions.assertThrows(VideoNotFoundException.class, () -> updatePartialVideoService.updateVideoAlreadyExists(video));
        Assertions.assertEquals("Não existe vídeo com id 1", resultException.getMessage());
    }

    @Test
    void video_exists_then_updated() {
        Video video = new Video(1L, "Teste1", "D1", "http://localhost",
                new Category(1L, "LIVRE", "BLUE"));
        UpdatePartialVideoPortOut updatePartialVideoPortOut = new UpdatePartialVideoPortOut() {
            @Override
            public Video updateAlreadyExists(Video video) {
                return video;
            }

            @Override
            public Boolean notExistsVideoById(Long id) {
                return false;
            }
        };
        UpdatePartialVideoService updatePartialVideoService = new UpdatePartialVideoService(updatePartialVideoPortOut);

        Video result = updatePartialVideoService.updateVideoAlreadyExists(video);

        Assertions.assertEquals(video, result);
    }
}