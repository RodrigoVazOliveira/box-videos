package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.commons.enumerations.MessageDeleteEnum;
import dev.rvz.boxvideos.core.domain.commons.responses.Message;
import dev.rvz.boxvideos.port.in.category.DeleteCategoryByIdPortIn;
import dev.rvz.boxvideos.port.out.category.DeleteCategoryByIdPortOut;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCategoryByIdService implements DeleteCategoryByIdPortIn {
    private final Logger LOGGER = Logger.getLogger(DeleteCategoryByIdService.class.getName());
    private final DeleteCategoryByIdPortOut deleteCategoryByIdPortOut;

    public DeleteCategoryByIdService(DeleteCategoryByIdPortOut deleteCategoryByIdPortOut) {
        this.deleteCategoryByIdPortOut = deleteCategoryByIdPortOut;
    }

    @Override
    public Message deleteById(Long id) {
        LOGGER.info("deleteById - id %d".formatted(id));
        if (deleteCategoryByIdPortOut.notExistsCategoryById(id)) {
            LOGGER.log(Level.WARNING, "category not found");
            throw new CategoryNotFoundException("categoria com id %d não existe.".formatted(id));


        }

        deleteCategoryByIdPortOut.deleteById(id);

        return new Message(MessageDeleteEnum.SUCCESS.getMessage());
    }
}
