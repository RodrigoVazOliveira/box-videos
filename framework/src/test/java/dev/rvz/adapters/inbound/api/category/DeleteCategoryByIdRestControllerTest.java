package dev.rvz.adapters.inbound.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.category.DeleteCategoryByIdRestController;
import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.commons.enumerations.MessageDeleteEnum;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseException;
import dev.rvz.boxvideos.core.domain.commons.responses.Message;
import dev.rvz.boxvideos.port.in.category.DeleteCategoryByIdPortIn;
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
        DeleteCategoryByIdRestController.class,
        DeleteCategoryByIdPortIn.class,
        ExceptionHandlerDefaultRest.class
})
public class DeleteCategoryByIdRestControllerTest {

    @MockBean
    private DeleteCategoryByIdPortIn deleteCategoryByIdPortIn;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void not_found_category() throws Exception {
        ResponseException responseException = new ResponseException(404, "categoria com id 1 não existe.");
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(responseException);
        Mockito.when(deleteCategoryByIdPortIn.deleteById(Mockito.any())).thenThrow(
                new CategoryNotFoundException("categoria com id 1 não existe.")
        );


        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1/"))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }

    @Test
    void category_deleted_with_success() throws Exception {
        Message message = new Message(MessageDeleteEnum.SUCCESS.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(message);
        Mockito.when(deleteCategoryByIdPortIn.deleteById(Mockito.any())).thenReturn(message);

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1/"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }
}
