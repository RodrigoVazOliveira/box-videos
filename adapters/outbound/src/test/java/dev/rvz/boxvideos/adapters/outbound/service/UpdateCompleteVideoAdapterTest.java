package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToVideoEntityMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UpdateCompleteVideoAdapterTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoToVideoEntityMapper videoToVideoEntityMapper;

    @Mock
    private VideoEntityToVideoMapper videoEntityToVideoMapper;

    @InjectMocks
    private UpdateCompleteVideoAdapter updateCompleteVideoAdapter;

    @Test
    void test_exists_video_by_id_is_true() {
        Mockito.when(videoRepository.existsById(Mockito.any())).thenReturn(true);
        Boolean resultExists = updateCompleteVideoAdapter.existsVideoById(1L);

        Assertions.assertTrue(resultExists);
    }

    @Test
    void test_exists_video_by_id_is_false() {
        Mockito.when(videoRepository.existsById(Mockito.any())).thenReturn(false);
        Boolean resultExists = updateCompleteVideoAdapter.existsVideoById(1L);

        Assertions.assertFalse(resultExists);
    }

    @Test
    void test_create_video_if_not_exists_with_sucess() {
        Video video = new Video(1L, "Teste1", "Teste1", "http://localhost:000");
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(video.id());
        videoEntity.setTitle(video.title());
        videoEntity.setDescription(video.description());
        videoEntity.setUrl(video.url());

        Mockito.when(videoToVideoEntityMapper.to(Mockito.any())).thenReturn(videoEntity);
        Mockito.when(videoRepository.save(Mockito.any())).thenReturn(videoEntity);
        Mockito.when(videoEntityToVideoMapper.to(Mockito.any())).thenReturn(video);

        Video result = updateCompleteVideoAdapter.createVideoIfNotExists(video);

        Assertions.assertEquals(video, result);
    }

    @Test
    void test_update_already_exists() {
        Video video = new Video(1L, "Teste1", "Teste1", "http://localhost:000");
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(video.id());
        videoEntity.setTitle(video.title());
        videoEntity.setDescription(video.description());
        videoEntity.setUrl(video.url());

        Mockito.when(videoRepository.findById(Mockito.any())).thenReturn(Optional.of(videoEntity));
        Mockito.when(videoRepository.save(Mockito.any())).thenReturn(videoEntity);
        Mockito.when(videoEntityToVideoMapper.to(Mockito.any())).thenReturn(video);

        Video result = updateCompleteVideoAdapter.updateAlreadyExists(video);

        Assertions.assertEquals(video, result);
    }
}