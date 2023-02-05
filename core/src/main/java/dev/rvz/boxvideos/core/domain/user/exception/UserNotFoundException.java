package dev.rvz.boxvideos.core.domain.user.exception;

public class UserNotFoundException extends RuntimeException {
    private final Integer status = 403;

    public UserNotFoundException(String message) {
        super(message);
    }

    public Integer getStatus() {
        return status;
    }
}