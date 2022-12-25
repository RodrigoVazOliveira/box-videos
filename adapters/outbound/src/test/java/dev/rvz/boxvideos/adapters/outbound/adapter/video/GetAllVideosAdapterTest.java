package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoEntityToIterableVideoMapper;
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

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@PropertySource({
        "classpath:application.properties"
})
@ContextConfiguration(classes = {
        VideoRepository.class,
        IterableVideoEntityToIterableVideoMapper.class,
        GetAllVideosAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class GetAllVideosAdapterTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper;

    @Autowired
    private GetAllVideosAdapter getAllVideosAdapter;

    @Test
    void test_get_all_videos_with_success() {
        VideoEntity videoEntityOne = new VideoEntity();
        videoEntityOne.setTitle("titulo 1");
        videoEntityOne.setDescription("descricao 1");
        videoEntityOne.setUrl("http://localhost");

        VideoEntity videoEntityTwo = new VideoEntity();
        videoEntityTwo.setTitle("titulo 1");
        videoEntityTwo.setDescription("descricao 1");
        videoEntityTwo.setUrl("http://localhost");
        videoRepository.save(videoEntityOne);
        videoRepository.save(videoEntityTwo);

        Iterable<Video> result = getAllVideosAdapter.execute();
        List<Video> videos = new ArrayList<>();
        result.iterator().forEachRemaining(videos::add);

        Assertions.assertFalse(videos.isEmpty());
        Assertions.assertEquals(2, videos.size());

        videoRepository.deleteAll();
    }
}