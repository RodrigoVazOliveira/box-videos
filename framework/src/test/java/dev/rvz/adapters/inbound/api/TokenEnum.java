package dev.rvz.adapters.inbound.api;

public enum TokenEnum {
    AUTORIZATION("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aXRvcl9zaWx2YUBnbWFpbC5jb20iLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6InZpdG9yX3NpbHZhQGdtYWlsLmNvbSIsImV4cCI6MTY3NTcwMzg4OX0.zWzv1MY7yv6-xE_yPdFLZp-aHBLGinu5v8ugvKfWFbtbpxxZjSw4kC0FzBERniIrLchm2oXSlew-EZn_JW2ZwA");

    private final String name;
    private final String value;

    TokenEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
