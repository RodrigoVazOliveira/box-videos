package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetVideoByIdAdapterTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoEntityToVideoMapper videoEntityToVideoMapper;

    @InjectMocks
    private GetVideoByIdAdapter getVideoByIdAdapter;

    @Test
    void test_get_video_by_id_with_success() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(1L);

        Video video = new Video(videoEntity.getId(), videoEntity.getTitle(), videoEntity.getDescription(), videoEntity.getUrl());

        Mockito.when(videoRepository.findById(Mockito.any())).thenReturn(Optional.of(videoEntity));
        Mockito.when(videoEntityToVideoMapper.to(Mockito.any())).thenReturn(video);

        Video result = getVideoByIdAdapter.execute(1L);

        Assertions.assertEquals(video, result);
    }

    @Test
    void test_get_video_by_id_not_foudn() {
        Mockito.when(videoRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        VideoNotFoundException result = assertThrows(VideoNotFoundException.class, () -> {
            getVideoByIdAdapter.execute(1L);
        });

        Assertions.assertEquals("Não existe vídeo com id 1", result.getMessage());
    }
}