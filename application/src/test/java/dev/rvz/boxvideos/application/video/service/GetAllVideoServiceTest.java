package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.GetAllVideosPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GetAllVideoServiceTest {

    @Test
    void test_get_all_videos_with_success() {
        GetAllVideosPortOut getAllVideosPortOut = () -> {
            Category category = new Category(1L, "LIVRE", "BLUE");
            return Arrays.asList(
                    new Video(1L, "Teste 1", "Video 1", "http://video1.com.br", category),
                    new Video(2L, "Teste 2", "Video 2", "http://video2.com.br", category),
                    new Video(3L, "Teste 3", "Video 3", "http://video3.com.br", category)
            );
        };

        GetAllVideoService getAllVideoService = new GetAllVideoService(getAllVideosPortOut);
        Iterable<Video> resultAllVideos = getAllVideoService.execute();
        List<Video> videos = new ArrayList<>();
        resultAllVideos.forEach(videos::add);


        Assertions.assertFalse(videos.isEmpty());
        Assertions.assertEquals(3, videos.size());
    }
}