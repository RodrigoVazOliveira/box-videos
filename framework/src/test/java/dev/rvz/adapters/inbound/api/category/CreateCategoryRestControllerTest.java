package dev.rvz.adapters.inbound.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CreateCategoryRequestToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.category.CreateCategoryRestController;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.InfoValidationInput;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseInputException;
import dev.rvz.boxvideos.core.domain.commons.exception.ValidateInputException;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
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
        CreateCategoryPortIn.class,
        CreateCategoryRequestToCategoryMapper.class,
        CategoryToCategoryResponseMapper.class,
        ExceptionHandlerDefaultRest.class,
        CreateCategoryRestController.class
})
class CreateCategoryRestControllerTest {

    @MockBean
    private CreateCategoryPortIn createCategoryPortIn;

    @MockBean
    private CreateCategoryRequestToCategoryMapper createCategoryRequestToCategoryMapper;

    @MockBean
    private CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_category_with_sucess_handred_two_ok() throws Exception {
        CreateCategoryRequest categoryRequest = new CreateCategoryRequest("SERIE", "RED");
        Category category = new Category(null, "SERIE", "RED");
        Category newCategory = new Category(1L, "SERIE", "RED");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "SERIE", "RED");

        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(categoryRequest);
        String response = objectMapper.writeValueAsString(categoryResponse);

        Mockito.when(createCategoryRequestToCategoryMapper.to(Mockito.any())).thenReturn(category);
        Mockito.when(createCategoryPortIn.create(Mockito.any())).thenReturn(newCategory);
        Mockito.when(categoryToCategoryResponseMapper.to(Mockito.any())).thenReturn(categoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                ).andExpect(
                        MockMvcResultMatchers.header().string("Location", "http://localhost/categories/1/")
                );
    }

    @Test
    void create_category_hudred_four_title_with_minor_three_character() throws Exception {
        CreateCategoryRequest categoryRequest = new CreateCategoryRequest("SE", "RED");
        Category category = new Category(null, "SERIE", "RED");

        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(categoryRequest);


        List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        infoValidationInputs.add(new InfoValidationInput("title", "O cmapo title deve ter no mínimo 3 de caracteres."));

        Mockito.when(createCategoryRequestToCategoryMapper.to(Mockito.any())).thenReturn(category);
        Mockito.when(createCategoryPortIn.create(Mockito.any())).thenThrow(
                new ValidateInputException(infoValidationInputs)
        );

        ResponseInputException responseInputException = new ResponseInputException(infoValidationInputs);

        String response = objectMapper.writeValueAsString(responseInputException);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }


}
