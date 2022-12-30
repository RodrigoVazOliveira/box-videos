package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.SearchVideoByTitlePortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SearchVideoByTitleServiceTest {

    @Test
    void test_search_video_not_found() {
        SearchVideoByTitlePortOut searchVideoByTitlePortOut = title -> new ArrayList<>();
        SearchVideoByTitleService searchVideoByTitleService = new SearchVideoByTitleService(searchVideoByTitlePortOut);

        VideoNotFoundException exceptionResult = Assertions.assertThrows(VideoNotFoundException.class, () -> {
            searchVideoByTitleService.run("teste 1");
        });

        Assertions.assertNotNull(exceptionResult);
        Assertions.assertEquals(exceptionResult.getMessage(),
                "Não foi enctrado nenhum video com o título teste 1");
    }

    @Test
    void test_search_video_by_title_sucess() {
        SearchVideoByTitlePortOut searchVideoByTitlePortOut = new SearchVideoByTitlePortOut() {
            @Override
            public Iterable<Video> run(String title) {
                return List.of(
                        new Video(1L, "teste 1", "descricao 1", "localhost:8080",
                                new Category(1L, "LIVRE", "WHITE"))
                );
            }
        };
        SearchVideoByTitleService searchVideoByTitleService = new SearchVideoByTitleService(searchVideoByTitlePortOut);

        Iterable<Video> resultVideos = searchVideoByTitleService.run("teste 1");
        List<Video> videos = new ArrayList<>();
        resultVideos.forEach(videos::add);


        Assertions.assertNotNull(resultVideos);
        Assertions.assertEquals(1, videos.size());
    }


}