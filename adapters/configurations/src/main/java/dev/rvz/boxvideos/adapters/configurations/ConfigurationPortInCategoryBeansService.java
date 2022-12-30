package dev.rvz.boxvideos.adapters.configurations;

import dev.rvz.boxvideos.application.category.service.*;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import dev.rvz.boxvideos.port.out.category.CreateCategoryPortOut;
import dev.rvz.boxvideos.port.out.category.DeleteCategoryByIdPortOut;
import dev.rvz.boxvideos.port.out.category.GetAllCategoriesPortOut;
import dev.rvz.boxvideos.port.out.category.GetCategoryByIdPortOut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConfigurationPortInCategoryBeansService {

    @Bean
    GetAllCategoriesService getAllCategoriesService(GetAllCategoriesPortOut getAllCategoriesPortOut) {
        return new GetAllCategoriesService(getAllCategoriesPortOut);
    }

    @Bean
    GetCategoryByIdService getCategoryByIdService(GetCategoryByIdPortOut getCategoryByIdPortOut) {
        return new GetCategoryByIdService(getCategoryByIdPortOut);
    }

    @Bean
    CreateCategoryService createCategoryService(CreateCategoryPortOut createCategoryPortOut) {
        return new CreateCategoryService(createCategoryPortOut);
    }

    @Bean
    DeleteCategoryByIdService deleteCategoryByIdService(DeleteCategoryByIdPortOut deleteCategoryByIdPortOut) {
        return new DeleteCategoryByIdService(deleteCategoryByIdPortOut);
    }

    @Bean
    UpdateCompleteCategoryService updateCompleteCategoryService(GetCategoryByIdPortOut getCategoryByIdPortOut,
                                                                CreateCategoryPortIn createCategoryPortIn) {
        return new UpdateCompleteCategoryService(getCategoryByIdPortOut, createCategoryPortIn);
    }

    @Bean
    UpdatePartialCategoryService updatePartialCategoryService(CreateCategoryPortIn createCategoryPortIn, GetCategoryByIdPortIn getCategoryByIdPortIn) {
        return new UpdatePartialCategoryService(createCategoryPortIn, getCategoryByIdPortIn);
    }
}
