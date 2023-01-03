package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoEntityToIterableVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
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
        SearchVideoByTitleAdapter.class,
        IterableVideoEntityToIterableVideoMapper.class,
        CategoryEntityToCategoryMapper.class,
        CategoryRepository.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class SearchVideoByTitleAdapterTest {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper;

    @Autowired
    private CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    @Autowired
    private SearchVideoByTitleAdapter searchVideoByTitleAdapter;


    @Test
    void get_video_by_title_with_success() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("LIVRE");
        categoryEntity.setColor("WHITE");

        categoryRepository.save(categoryEntity);

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Titulo 1");
        videoEntity.setDescription("Descricao 1");
        videoEntity.setUrl("http://localhost");
        videoEntity.setCategoryEntity(categoryEntity);

        videoRepository.save(videoEntity);


        Iterable<Video> videos = searchVideoByTitleAdapter.run(videoEntity.getTitle());

        List<Video> videosList = new ArrayList<>();
        videos.forEach(videosList::add);

        Assertions.assertNotNull(videos);
        Assertions.assertEquals(1, videosList.size());

        videoRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void get_video_by_title_with_result_empty() {

        Iterable<Video> videos = searchVideoByTitleAdapter.run("X");

        List<Video> videosList = new ArrayList<>();
        videos.forEach(videosList::add);

        Assertions.assertNotNull(videos);
        Assertions.assertEquals(0, videosList.size());
    }


}