package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.CreateVideoPortout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateVideoServiceTest {

    @Mock
    private CreateVideoPortout createVideoPortout;

    @InjectMocks
    private CreateVideoService createVideoService;

    @Test
    void test_video_return_with_success() {
        Video video = new Video(1L, "Testes 1", "Descrição", "http://meuvideo.comb.br");
        Mockito.when(createVideoPortout.execute(Mockito.any())).thenReturn(video);
        Video requestSaveVideo = new Video(1L, "Testes 1", "Descrição", "http://meuvideo.comb.br");
        Video result = createVideoService.execute(requestSaveVideo);

        Assertions.assertEquals(video, requestSaveVideo);
    }

}