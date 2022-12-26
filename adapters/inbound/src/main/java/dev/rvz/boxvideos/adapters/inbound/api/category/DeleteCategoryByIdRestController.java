package dev.rvz.boxvideos.adapters.inbound.api.category;

import dev.rvz.boxvideos.core.domain.commons.responses.Message;
import dev.rvz.boxvideos.port.in.category.DeleteCategoryByIdPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class DeleteCategoryByIdRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(DeleteCategoryByIdRestController.class);
    private final DeleteCategoryByIdPortIn deleteCategoryByIdPortIn;

    public DeleteCategoryByIdRestController(DeleteCategoryByIdPortIn deleteCategoryByIdPortIn) {
        this.deleteCategoryByIdPortIn = deleteCategoryByIdPortIn;
    }

    @DeleteMapping("/{id}/")
    @ResponseStatus(HttpStatus.OK)
    Message deleteById(@PathVariable Long id) {
        LOGGER.info("deleteById - id {}", id);
        Message message = deleteCategoryByIdPortIn.deleteById(id);
        LOGGER.info("deleteById - message {}", message);
        
        return message;
    }
}
