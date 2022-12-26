package dev.rvz.adapters.inbound.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.AllCategoriesToAllCategoriesResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.inbound.api.category.GetAllCategoriesRestController;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.GetAllCategoriesPortIn;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        GetAllCategoriesPortIn.class,
        AllCategoriesToAllCategoriesResponseMapper.class,
        GetAllCategoriesRestController.class
})
class GetAllCategoriesRestControllerTest {

    @MockBean
    private GetAllCategoriesPortIn getAllCategoriesPortIn;

    @MockBean
    private AllCategoriesToAllCategoriesResponseMapper allCategoriesToAllCategoriesResponseMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_get_all_categories_with_sucess_handred_two_ok() throws Exception {
        Iterable<Category> categories = Arrays.asList(
                new Category(1L, "LIVRE", "BLUE"),
                new Category(2L, "FILME", "GREEN")
        );

        Iterable<CategoryResponse> categoryResponses = Arrays.asList(
                new CategoryResponse(1L, "LIVRE", "BLUE"),
                new CategoryResponse(2L, "FILME", "GREEN")
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(categoryResponses);

        Mockito.when(getAllCategoriesPortIn.execute()).thenReturn(categories);
        Mockito.when(allCategoriesToAllCategoriesResponseMapper.to(Mockito.any())).thenReturn(categoryResponses);
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }
}
