package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToVideoEntityMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateVideoAdapterTest {
    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoEntityToVideoMapper videoEntityToVideoMapper;

    @Mock
    private VideoToVideoEntityMapper videoToVideoEntityMapper;

    @InjectMocks
    private CreateVideoAdapter createVideoAdapter;

    @Test
    void save_with_success() {
        Video video = new Video(1L, "", "", "");
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(video.id());
        videoEntity.setTitle(video.title());
        videoEntity.setDescription(video.description());
        videoEntity.setUrl(video.url())
        ;
        Mockito.when(videoToVideoEntityMapper.to(Mockito.any())).thenReturn(videoEntity);
        Mockito.when(videoEntityToVideoMapper.to(videoEntity)).thenReturn(video);

        Video result = createVideoAdapter.execute(video);
    }
}