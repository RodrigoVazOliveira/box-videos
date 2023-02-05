package dev.rvz.boxvideos.core.domain.user.enumerations;

public enum AccessRoleEnum {
    ADMIN(1, "Administrador"), USER(1, "Usuário");


    private final Integer id;
    private final String description;

    AccessRoleEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
