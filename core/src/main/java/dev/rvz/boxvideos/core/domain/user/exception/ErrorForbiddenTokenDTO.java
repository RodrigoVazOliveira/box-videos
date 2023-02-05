package dev.rvz.boxvideos.core.domain.user.exception;

final public class ErrorForbiddenTokenDTO {
    private final String message = "Token inválido";
    private final Integer statusCode = 403;
    private final String error = "FORBIDDEN";
    private final String path;

    public ErrorForbiddenTokenDTO(String path) {
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
