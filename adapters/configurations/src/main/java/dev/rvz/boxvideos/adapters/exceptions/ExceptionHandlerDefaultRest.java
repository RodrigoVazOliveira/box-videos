package dev.rvz.boxvideos.adapters.exceptions;

import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseException;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseInputException;
import dev.rvz.boxvideos.core.domain.commons.exception.ValidateInputException;
import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerDefaultRest {

    @ExceptionHandler({VideoNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseException videoNotFoundException(VideoNotFoundException exception) {
        return new ResponseException(404, exception.getMessage());
    }

    @ExceptionHandler({ValidateInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseInputException eesponseInputException(ValidateInputException e) {
        return new ResponseInputException(e.getInfoValidationInputs());
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseException eategoryNotFoundException(CategoryNotFoundException e) {
        return new ResponseException(404, e.getMessage());
    }

}
