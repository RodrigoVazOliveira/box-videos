package dev.rvz.adapters.inbound.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.adapters.inbound.api.TokenEnum;
import dev.rvz.adapters.inbound.api.commons.MockSpringSecurity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CreateCategoryRequestWithIdToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.category.UpdateCompleteCategoryRestController;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.InfoValidationInput;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseInputException;
import dev.rvz.boxvideos.core.domain.commons.exception.ValidateInputException;
import dev.rvz.boxvideos.port.in.category.UpdateCompleteCategoryPortIn;
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

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        UpdateCompleteCategoryPortIn.class,
        CreateCategoryRequestWithIdToCategoryMapper.class,
        CategoryToCategoryResponseMapper.class,
        UpdateCompleteCategoryRestController.class,
        ExceptionHandlerDefaultRest.class
})
class UpdateCompleteCategoryRestControllerTest extends MockSpringSecurity {

    @MockBean
    UpdateCompleteCategoryPortIn updateCompleteCategoryPortIn;

    @MockBean
    CreateCategoryRequestWithIdToCategoryMapper createCategoryRequestWithIdToCategoryMapper;

    @MockBean
    CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void error_validation_data_category_exists() throws Exception {
        final CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("FI", "RED");
        final Category category = new Category(1L, createCategoryRequest.title(), createCategoryRequest.color());
        final ObjectMapper objectMapper = new ObjectMapper();
        final String request = objectMapper.writeValueAsString(createCategoryRequest);

        final List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        infoValidationInputs.add(new InfoValidationInput("title", "O campo title deve ter no mínimo 3 de caracteres."));

        final ResponseInputException responseInputException = new ResponseInputException(infoValidationInputs);

        final String response = objectMapper.writeValueAsString(responseInputException);

        Mockito.when(createCategoryRequestWithIdToCategoryMapper.to(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(updateCompleteCategoryPortIn.existsCategoryById(Mockito.any())).thenReturn(true);
        Mockito.when(updateCompleteCategoryPortIn.update(Mockito.any(), Mockito.any())).thenThrow(
                new ValidateInputException(infoValidationInputs)
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                        .content(request))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }

    @Test
    void error_validation_data_category_not_exists() throws Exception {
        final CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("FI", "RED");
        final Category category = new Category(1L, createCategoryRequest.title(), createCategoryRequest.color());
        final ObjectMapper objectMapper = new ObjectMapper();
        final String request = objectMapper.writeValueAsString(createCategoryRequest);

        final List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        infoValidationInputs.add(new InfoValidationInput("title", "O campo title deve ter no mínimo 3 de caracteres."));

        final ResponseInputException responseInputException = new ResponseInputException(infoValidationInputs);

        final String response = objectMapper.writeValueAsString(responseInputException);

        Mockito.when(createCategoryRequestWithIdToCategoryMapper.to(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(updateCompleteCategoryPortIn.existsCategoryById(Mockito.any())).thenReturn(false);
        Mockito.when(updateCompleteCategoryPortIn.update(Mockito.any(), Mockito.any())).thenThrow(
                new ValidateInputException(infoValidationInputs)
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                        .content(request))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }

    @Test
    void create_category_not_exists() throws Exception {
        final CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("FILME", "RED");
        final Category category = new Category(1L, createCategoryRequest.title(), createCategoryRequest.color());
        final CategoryResponse categoryResponse = new CategoryResponse(category.id(), category.title(), category.color());
        final ObjectMapper objectMapper = new ObjectMapper();
        final String request = objectMapper.writeValueAsString(createCategoryRequest);
        final String response = objectMapper.writeValueAsString(categoryResponse);

        Mockito.when(createCategoryRequestWithIdToCategoryMapper.to(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(updateCompleteCategoryPortIn.existsCategoryById(Mockito.any())).thenReturn(false);
        Mockito.when(updateCompleteCategoryPortIn.update(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(categoryToCategoryResponseMapper.to(Mockito.any())).thenReturn(categoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                        .content(request))
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }

    @Test
    void update_category_exists() throws Exception {
        final CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("FILME", "RED");
        final Category category = new Category(1L, createCategoryRequest.title(), createCategoryRequest.color());
        final CategoryResponse categoryResponse = new CategoryResponse(category.id(), category.title(), category.color());
        final ObjectMapper objectMapper = new ObjectMapper();
        final String request = objectMapper.writeValueAsString(createCategoryRequest);
        final String response = objectMapper.writeValueAsString(categoryResponse);

        Mockito.when(createCategoryRequestWithIdToCategoryMapper.to(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(updateCompleteCategoryPortIn.existsCategoryById(Mockito.any())).thenReturn(true);
        Mockito.when(updateCompleteCategoryPortIn.update(Mockito.any(), Mockito.any())).thenReturn(category);
        Mockito.when(categoryToCategoryResponseMapper.to(Mockito.any())).thenReturn(categoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                        .content(request))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }
}
