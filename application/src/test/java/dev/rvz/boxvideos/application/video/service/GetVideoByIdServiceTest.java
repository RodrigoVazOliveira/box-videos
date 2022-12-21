package dev.rvz.boxvideos.application.video.service;

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
        Mockito.when(getVideoByIdPortOut.execute(Mockito.any())).thenReturn(video);

        Video result = getVideoByIdService.execute(1L);

        Assertions.assertEquals(video, result);
    }
}