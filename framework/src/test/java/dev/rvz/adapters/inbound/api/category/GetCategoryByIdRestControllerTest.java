package dev.rvz.adapters.inbound.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.category.GetCategoryByIdRestController;
import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseException;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
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

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        ExceptionHandlerDefaultRest.class,
        GetCategoryByIdPortIn.class,
        CategoryToCategoryResponseMapper.class,
        GetCategoryByIdRestController.class
})
class GetCategoryByIdRestControllerTest {

    @MockBean
    private GetCategoryByIdPortIn getCategoryByIdPortIn;

    @MockBean
    private CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_get_by_id_with_sucess_handred_two_ok() throws Exception {
        Category category = new Category(1L, "LIVRE", "BLUE");
        CategoryResponse categoryResponse = new CategoryResponse(category.id(), category.title(), category.color());

        Mockito.when(getCategoryByIdPortIn.getCategoryById(Mockito.any())).thenReturn(category);
        Mockito.when(categoryToCategoryResponseMapper.to(Mockito.any())).thenReturn(categoryResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(categoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/1/"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }

    @Test
    void test_get_by_id_with_error_not_found() throws Exception {
        Mockito.when(getCategoryByIdPortIn.getCategoryById(Mockito.any())).thenThrow(
                new CategoryNotFoundException("categoria com id 1 não existe.")
        );
        ResponseException responseException = new ResponseException(404, "categoria com id 1 não existe.");

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(responseException);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/1/"))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }


}
