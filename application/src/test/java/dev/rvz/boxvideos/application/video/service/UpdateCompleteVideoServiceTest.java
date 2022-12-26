package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.CreateVideoPortIn;
import dev.rvz.boxvideos.port.out.video.GetVideoByIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UpdateCompleteVideoServiceTest {

    @Test
    void update_video_already_exists() {
        Video video = new Video(1L, "Testes 1", "Descricao 1", "http://localhost");
        CreateVideoPortIn createVideoPortIn = video1 -> new Video(video1.id(), video1.title(), video1.description(), video1.url());
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

        UpdateCompleteVideoService updateCompleteVideoService = new UpdateCompleteVideoService(createVideoPortIn, getVideoByIdPortOut);
        Video result = updateCompleteVideoService.execute(video, true);

        Assertions.assertEquals(video, result);
    }

    @Test
    void update_video_not_exists() {
        Video video = new Video(1L, "Testes 1", "Descricao 1", "http://localhost");
        CreateVideoPortIn createVideoPortIn = video1 -> new Video(1L, video1.title(), video1.description(), video1.url());
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

        UpdateCompleteVideoService updateCompleteVideoService = new UpdateCompleteVideoService(createVideoPortIn, getVideoByIdPortOut);
        Video result = updateCompleteVideoService.execute(video, false);

        Assertions.assertEquals(video, result);
    }

    @Test
    void exists_video() {
        CreateVideoPortIn createVideoPortIn = video1 -> new Video(1L, video1.title(), video1.description(), video1.url());
        GetVideoByIdPortOut getVideoByIdPortOut = new GetVideoByIdPortOut() {
            @Override
            public Video execute(Long id) {
                return null;
            }

            @Override
            public Boolean notExistsVideoById(Long id) {
                return false;
            }

            ;
        };

        UpdateCompleteVideoService updateCompleteVideoService = new UpdateCompleteVideoService(createVideoPortIn, getVideoByIdPortOut);
        Boolean result = updateCompleteVideoService.videoExists(1L);

        Assertions.assertTrue(result);
    }

    @Test
    void not_exists_video() {
        CreateVideoPortIn createVideoPortIn = video1 -> new Video(1L, video1.title(), video1.description(), video1.url());
        GetVideoByIdPortOut getVideoByIdPortOut = new GetVideoByIdPortOut() {
            @Override
            public Video execute(Long id) {
                return null;
            }

            @Override
            public Boolean notExistsVideoById(Long id) {
                return true;
            }

            ;
        };

        UpdateCompleteVideoService updateCompleteVideoService = new UpdateCompleteVideoService(createVideoPortIn, getVideoByIdPortOut);
        Boolean result = updateCompleteVideoService.videoExists(1L);

        Assertions.assertFalse(result);
    }
}