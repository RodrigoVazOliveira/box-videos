package dev.rvz.boxvideos.port.in.category;

import dev.rvz.boxvideos.core.domain.commons.responses.Message;

public interface DeleteCategoryByIdPortIn {
    Message deleteById(Long id);

}
