package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.GetAllCategoriesPortIn;
import dev.rvz.boxvideos.port.out.category.GetAllCategoriesPortOut;

public class GetAllCategoriesService implements GetAllCategoriesPortIn {
    private final GetAllCategoriesPortOut getAllCategoriesPortOut;

    public GetAllCategoriesService(GetAllCategoriesPortOut getAllCategoriesPortOut) {
        this.getAllCategoriesPortOut = getAllCategoriesPortOut;
    }

    @Override
    public Iterable<Category> execute() {
        return getAllCategoriesPortOut.execute();
    }
}
