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
        IterableVideoEntityToIterableVideoMapper.class,
        CategoryEntityToCategoryMapper.class,
        GetVideoByCategoryIdAdapter.class,
        CategoryRepository.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class GetVideoByCategoryIdAdapterTest {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper;

    @Autowired
    CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    @Autowired
    GetVideoByCategoryIdAdapter getVideoByCategoryIdAdapter;

    @Test
    void get_video_with_success() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("FILME");
        categoryEntity.setColor("WHITE");
        categoryRepository.save(categoryEntity);

        VideoEntity videoEntityOne = new VideoEntity();
        videoEntityOne.setTitle("O caçador");
        videoEntityOne.setDescription("Descricao 1");
        videoEntityOne.setUrl("http://localhost");
        videoEntityOne.setCategoryEntity(categoryEntity);

        VideoEntity videoEntityTwo = new VideoEntity();
        videoEntityTwo.setTitle("O militante");
        videoEntityTwo.setDescription("Descricao 1");
        videoEntityTwo.setUrl("http://localhost");
        videoEntityTwo.setCategoryEntity(categoryEntity);
        videoRepository.saveAll(List.of(videoEntityOne, videoEntityTwo));

        Iterable<Video> videosIterable = getVideoByCategoryIdAdapter.run(categoryEntity.getId());
        List<Video> videos = new ArrayList<>();
        videosIterable.forEach(videos::add);


        Assertions.assertNotNull(videosIterable);
        Assertions.assertEquals(2, videos.size());

        videoRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}