package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import dev.rvz.boxvideos.port.in.video.GetVideoByCategoryIdPortIn;
import dev.rvz.boxvideos.port.out.video.GetVideoByCategoryIdPortOut;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetVideoByCategoryIdService implements GetVideoByCategoryIdPortIn {
    private final Logger LOGGER = Logger.getLogger(GetVideoByCategoryIdService.class.getName());
    private final GetCategoryByIdPortIn getCategoryByIdPortIn;
    private final GetVideoByCategoryIdPortOut getVideoByCategoryIdPortOut;

    public GetVideoByCategoryIdService(GetCategoryByIdPortIn getCategoryByIdPortIn, GetVideoByCategoryIdPortOut getVideoByCategoryIdPortOut) {
        this.getCategoryByIdPortIn = getCategoryByIdPortIn;
        this.getVideoByCategoryIdPortOut = getVideoByCategoryIdPortOut;
    }

    @Override
    public Iterable<Video> run(Long id) {
        LOGGER.log(Level.INFO, "run - category id %d".formatted(id));
        getCategoryByIdPortIn.getCategoryById(id);

        Iterable<Video> videos = getVideoByCategoryIdPortOut.run(id);

        return videos;
    }
}
