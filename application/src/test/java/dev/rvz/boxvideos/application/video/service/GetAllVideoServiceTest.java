package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.GetAllVideosPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class GetAllVideoServiceTest {

    @Mock
    private GetAllVideosPortOut getAllVideosPortOut;

    @InjectMocks
    private GetAllVideoService getAllVideoService;

    @Test
    void test_get_all_videos_with_success() {
        Iterable<Video> allVideos = Arrays.asList(
                new Video(1L, "Teste 1", "Video 1", "http://video1.com.br"),
                new Video(1L, "Teste 2", "Video 2", "http://video2.com.br"),
                new Video(1L, "Teste 3", "Video 3", "http://video3.com.br")
        );

        Mockito.when(getAllVideosPortOut.execute()).thenReturn(allVideos);
        Iterable<Video> resultAllVideos = getAllVideoService.execute();

        Assertions.assertEquals(allVideos, resultAllVideos);
    }
}