package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.video.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@PropertySource({
        "classpath:application.properties"
})
@ContextConfiguration(classes = {
        VideoRepository.class, VideoEntityToVideoMapper.class, UpdatePartialVideoAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class UpdatePartialVideoAdapterTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoEntityToVideoMapper videoEntityToVideoMapper;

    @Autowired
    private UpdatePartialVideoAdapter updatePartialVideoAdapter;

    @Test
    void not_exists_is_true() {
        Boolean notExistsVideoById = updatePartialVideoAdapter.notExistsVideoById(34324234L);

        Assertions.assertTrue(notExistsVideoById);
    }

    @Test
    void not_exists_is_false() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");

        videoRepository.save(videoEntity);

        Boolean notExistsVideoById = updatePartialVideoAdapter.notExistsVideoById(videoEntity.getId());

        Assertions.assertFalse(notExistsVideoById);
        videoRepository.deleteById(videoEntity.getId());
    }

    @Test
    void update_partial_with_video_exists_title_not_null_or_blank_or_empty() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");

        videoRepository.save(videoEntity);

        Video video = new Video(videoEntity.getId(), "Video 2", "", "");
        Video resultUpdate = updatePartialVideoAdapter.updateAlreadyExists(video);


        Assertions.assertEquals(video.title(), resultUpdate.title());
        Assertions.assertNotEquals(videoEntity.getTitle(), resultUpdate.title());
        videoRepository.deleteById(videoEntity.getId());
    }

    @Test
    void update_partial_with_video_exists_description_not_null_or_blank_or_empty() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");
        videoRepository.save(videoEntity);

        Video video = new Video(videoEntity.getId(), null, "Description", "");
        Video resultUpdate = updatePartialVideoAdapter.updateAlreadyExists(video);


        Assertions.assertEquals(video.description(), resultUpdate.description());
        Assertions.assertNotEquals(videoEntity.getDescription(), resultUpdate.description());
        videoRepository.deleteById(videoEntity.getId());
    }

    @Test
    void update_partial_with_video_exists_url_not_null_or_blank_or_empty() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");

        videoRepository.save(videoEntity);

        Video video = new Video(videoEntity.getId(), null, "", "http://localhost/1");
        Video resultUpdate = updatePartialVideoAdapter.updateAlreadyExists(video);


        Assertions.assertEquals(video.url(), resultUpdate.url());
        Assertions.assertNotEquals(videoEntity.getUrl(), resultUpdate.url());
        videoRepository.deleteById(videoEntity.getId());
    }
}