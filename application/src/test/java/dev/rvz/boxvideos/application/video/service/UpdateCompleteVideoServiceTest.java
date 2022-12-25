package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.UpdateCompleteVideoPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UpdateCompleteVideoServiceTest {

    @Test
    void update_video_already_exists() {
        Video video = new Video(1L, "Testes 1", "Descricao 1", "http://localhost");
        UpdateCompleteVideoPortOut updateCompleteVideoPortOut = new UpdateCompleteVideoPortOut() {
            @Override
            public Video updateAlreadyExists(Video video) {
                return video;
            }

            @Override
            public Video createVideoIfNotExists(Video video) {
                return null;
            }

            @Override
            public Boolean existsVideoById(Long id) {
                return true;
            }
        };

        UpdateCompleteVideoService updateCompleteVideoService = new UpdateCompleteVideoService(updateCompleteVideoPortOut);
        Video result = updateCompleteVideoService.execute(video);

        Assertions.assertEquals(video, result);
    }

    @Test
    void update_video_not_exists() {
        Video video = new Video(1L, "Testes 1", "Descricao 1", "http://localhost");
        UpdateCompleteVideoPortOut updateCompleteVideoPortOut = new UpdateCompleteVideoPortOut() {
            @Override
            public Video updateAlreadyExists(Video video) {
                return null;
            }

            @Override
            public Video createVideoIfNotExists(Video video) {
                return video;
            }

            @Override
            public Boolean existsVideoById(Long id) {
                return false;
            }
        };

        UpdateCompleteVideoService updateCompleteVideoService = new UpdateCompleteVideoService(updateCompleteVideoPortOut);
        Video result = updateCompleteVideoService.execute(video);

        Assertions.assertEquals(video, result);
    }
}