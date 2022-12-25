package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.DeleteVideoByIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeleteVideoByIdServiceTest {

    @Test
    void test_video_not_found() {
        DeleteVideoByIdPortOut deleteVideoByIdPortOut = new DeleteVideoByIdPortOut() {
            @Override
            public void deleteById(Video video) {

            }

            @Override
            public Boolean notExitsVideo(Long id) {
                return true;
            }
        };

        DeleteVideoByIdService deleteVideoByIdService = new DeleteVideoByIdService(deleteVideoByIdPortOut);

        VideoNotFoundException resultException = Assertions.assertThrows(VideoNotFoundException.class, () -> {
            deleteVideoByIdService.run(new Video(1L, "", "", ""));
        });

        Assertions.assertEquals("Não existe vídeo com id 1", resultException.getMessage());
    }

    @Test
    void test_video_deleted_with_success() {
        DeleteVideoByIdPortOut deleteVideoByIdPortOut = new DeleteVideoByIdPortOut() {
            @Override
            public void deleteById(Video video) {
                System.out.println("Video deletado " + video);
            }

            @Override
            public Boolean notExitsVideo(Long id) {
                return false;
            }
        };

        DeleteVideoByIdService deleteVideoByIdService = new DeleteVideoByIdService(deleteVideoByIdPortOut);
        Video video = new Video(1L, "", "", "");
        deleteVideoByIdService.run(video);

    }
}