package dev.rvz.boxvideos.core.domain.commons.enumerations;

public enum MessageDeleteEnum {
    SUCCESS("Recurso deletado com sucesso!");

    private String message;

    MessageDeleteEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
