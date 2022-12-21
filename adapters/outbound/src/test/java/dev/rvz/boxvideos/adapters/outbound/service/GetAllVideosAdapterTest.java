package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.IterableVideoEntityToIterableVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetAllVideosAdapterTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper;

    @InjectMocks
    private GetAllVideosAdapter getAllVideosAdapter;

    @Test
    void test_get_all_videos_with_success() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(1L);
        Iterable<VideoEntity> videoEntities = List.of(
                videoEntity
        );

        List<Video> videos = List.of(new Video(videoEntity.getId(), videoEntity.getTitle(), videoEntity.getDescription(), videoEntity.getUrl()));

        Mockito.when(videoRepository.findAll()).thenReturn(videoEntities);
        Mockito.when(iterableVideoEntityToIterableVideoMapper.to(Mockito.any()))
                .thenReturn(videos);

        Iterable<Video> result = getAllVideosAdapter.execute();

        Assertions.assertEquals(videos, result);
    }
}