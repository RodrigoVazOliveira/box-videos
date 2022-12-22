package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.GetVideoByIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetVideoByIdServiceTest {

    @Mock
    private GetVideoByIdPortOut getVideoByIdPortOut;

    @InjectMocks
    private GetVideoByIdService getVideoByIdService;

    @Test
    void test_get_video_by_id_with_success() {
        Video video = new Video(1L, "", "", "");
        Mockito.when(getVideoByIdPortOut.notExistsVideoById(Mockito.any())).thenReturn(false);
        Mockito.when(getVideoByIdPortOut.execute(Mockito.any())).thenReturn(video);

        Video result = getVideoByIdService.execute(1L);

        Assertions.assertEquals(video, result);
    }

    @Test
    void test_get_video_by_id_without_success() {
        Mockito.when(getVideoByIdPortOut.notExistsVideoById(Mockito.any())).thenReturn(true);

        VideoNotFoundException resultException = Assertions.assertThrows(VideoNotFoundException.class, () -> {
            getVideoByIdService.execute(1L);
        });

        Assertions.assertEquals("Não existe vídeo com id 1", resultException.getMessage());
    }
}