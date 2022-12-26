package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.InfoValidationInput;
import dev.rvz.boxvideos.core.domain.commons.exception.ValidateInputException;
import dev.rvz.boxvideos.port.out.category.CreateCategoryPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CreateCategoryServiceTest {

    @Test
    void create_category_without_sucess_size_title_incorret() {
        CreateCategoryPortOut createCategoryPortOut = category -> null;

        Category category = new Category(null, "1", "4324232323");
        CreateCategoryService createCategoryService = new CreateCategoryService(createCategoryPortOut);

        ValidateInputException resutlException = Assertions.assertThrows(ValidateInputException.class, () -> {
            createCategoryService.create(category);
        });

        List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        resutlException.getInfoValidationInputs().forEach(infoValidationInputs::add);

        Assertions.assertNotNull(resutlException);
        Assertions.assertEquals(1, infoValidationInputs.size());
        Assertions.assertEquals("title", infoValidationInputs.get(0).input());
        Assertions.assertEquals("O cmapo title deve ter no mínimo 3 de caracteres.", infoValidationInputs.get(0).message());

    }

    @Test
    void create_category_without_sucess_size_color_incorret() {
        CreateCategoryPortOut createCategoryPortOut = category -> null;

        Category category = new Category(null, "Titulo", "43");
        CreateCategoryService createCategoryService = new CreateCategoryService(createCategoryPortOut);

        ValidateInputException resutlException = Assertions.assertThrows(ValidateInputException.class, () -> {
            createCategoryService.create(category);
        });

        List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        resutlException.getInfoValidationInputs().forEach(infoValidationInputs::add);

        Assertions.assertNotNull(resutlException);
        Assertions.assertEquals(1, infoValidationInputs.size());
        Assertions.assertEquals("color", infoValidationInputs.get(0).input());
        Assertions.assertEquals("O cmapo color deve ter no mínimo 3 de caracteres.", infoValidationInputs.get(0).message());
    }

    @Test
    void create_category_without_sucess_title_null_and_color_blank_incorret() {
        CreateCategoryPortOut createCategoryPortOut = category -> null;

        Category category = new Category(null, null, "");
        CreateCategoryService createCategoryService = new CreateCategoryService(createCategoryPortOut);

        ValidateInputException resutlException = Assertions.assertThrows(ValidateInputException.class, () -> {
            createCategoryService.create(category);
        });

        List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        resutlException.getInfoValidationInputs().forEach(infoValidationInputs::add);

        Assertions.assertNotNull(resutlException);
        Assertions.assertEquals(2, infoValidationInputs.size());
        Assertions.assertEquals("title", infoValidationInputs.get(0).input());
        Assertions.assertEquals("color", infoValidationInputs.get(1).input());
        Assertions.assertEquals("O campo title está nulo. O campo title é obrigatório!", infoValidationInputs.get(0).message());
        Assertions.assertEquals("O campo color está em branco ou vazio. O campo color é obrigatório!", infoValidationInputs.get(1).message());
    }

    @Test
    void create_category_with_sucess() {
        CreateCategoryPortOut createCategoryPortOut = category -> new Category(1L, category.title(), category.color());

        Category category = new Category(null, "Titulo 1", "4324232323");
        CreateCategoryService createCategoryService = new CreateCategoryService(createCategoryPortOut);
        Category result = createCategoryService.create(category);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.title(), result.title());
        Assertions.assertEquals(category.color(), result.color());
        Assertions.assertEquals(1L, result.id());

    }


}