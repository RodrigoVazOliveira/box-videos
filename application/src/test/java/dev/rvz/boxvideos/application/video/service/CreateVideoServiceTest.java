package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.CreateVideoPortout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateVideoServiceTest {

    private final CreateVideoPortout createVideoPortout = new CreateVideoPortout() {
        @Override
        public Video execute(Video video) {
            return new Video(1L, video.title(), video.description(), video.url());
        }
    };

    @Test
    void test_video_return_with_success() {
        Video requestSaveVideo = new Video(null, "Testes 1", "Descrição", "http://meuvideo.comb.br");
        CreateVideoService createVideoService = new CreateVideoService(createVideoPortout);

        Video result = createVideoService.execute(requestSaveVideo);

        Assertions.assertEquals(requestSaveVideo.title(), result.title());
        Assertions.assertEquals(requestSaveVideo.description(), result.description());
        Assertions.assertEquals(requestSaveVideo.url(), result.url());
        Assertions.assertEquals(1L, result.id());
    }

}