package dev.rvz.adapters.inbound.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.adapters.inbound.api.TokenEnum;
import dev.rvz.adapters.inbound.api.commons.MockSpringSecurity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CreateCategoryRequestWithIdToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.category.UpdatePartialCategoryRestController;
import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseException;
import dev.rvz.boxvideos.port.in.category.UpdatePartialCategoryPortIn;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        UpdatePartialCategoryRestController.class,
        UpdatePartialCategoryPortIn.class,
        CreateCategoryRequestWithIdToCategoryMapper.class,
        CategoryToCategoryResponseMapper.class,
        ExceptionHandlerDefaultRest.class

})
class UpdatePartialCategoryRestControllerTest extends MockSpringSecurity {

    @MockBean
    UpdatePartialCategoryPortIn updatePartialCategoryPortIn;

    @MockBean
    CreateCategoryRequestWithIdToCategoryMapper createCategoryRequestWithIdToCategoryMapper;

    @MockBean
    CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_update_partial_with_success_200_OK() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("DOCUMENTARIO", "WHITE");
        final Category category = new Category(1L, "DOCUMENTARIO", "WHITE");
        final CategoryResponse categoryResponse = new CategoryResponse(category.id(), category.title(), category.color());
        final String requestJson = objectMapper.writeValueAsString(createCategoryRequest);
        final String responseJson = objectMapper.writeValueAsString(categoryResponse);

        Mockito.when(createCategoryRequestWithIdToCategoryMapper.to(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(updatePartialCategoryPortIn.update(Mockito.any())).thenReturn(category);
        Mockito.when(categoryToCategoryResponseMapper.to(Mockito.any())).thenReturn(categoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.patch("/categories/1/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.content().json(responseJson)
        );
    }

    @Test
    void test_update_partial_with_error_404() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ResponseException responseException = new ResponseException(404, "categoria com id 1 não existe.");
        final CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("DOCUMENTARIO", "WHITE");
        final Category category = new Category(1L, "DOCUMENTARIO", "WHITE");
        final String requestJson = objectMapper.writeValueAsString(createCategoryRequest);
        final String responseJson = objectMapper.writeValueAsString(responseException);

        Mockito.when(createCategoryRequestWithIdToCategoryMapper.to(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(updatePartialCategoryPortIn.update(Mockito.any()))
                .thenThrow(new CategoryNotFoundException("categoria com id 1 não existe."));

        mockMvc.perform(MockMvcRequestBuilders.patch("/categories/1/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andExpect(
                MockMvcResultMatchers.content().json(responseJson)
        );
    }
}
