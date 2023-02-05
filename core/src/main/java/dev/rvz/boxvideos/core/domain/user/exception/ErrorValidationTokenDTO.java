package dev.rvz.boxvideos.core.domain.user.exception;

final public class ErrorValidationTokenDTO {
    private final String message = "Token não informado";
    private final Integer statusCode = 400;
    private final String error = "BAD REQUEST";
    private final String path;


    public ErrorValidationTokenDTO(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
