package dev.rvz.boxvideos.adapters.configurations;

import dev.rvz.boxvideos.application.category.service.GetAllCategoriesService;
import dev.rvz.boxvideos.application.category.service.GetCategoryByIdService;
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
}
