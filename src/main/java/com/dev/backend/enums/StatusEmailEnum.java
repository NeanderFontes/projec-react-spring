package com.dev.backend.enums;

public enum StatusEmailEnum {
    SENT(1, "Email enviado com sucesso!"),
    ERROR(0, "Erro ao enviar email!");

    private final int code;
    private final String statusEmail;

    StatusEmailEnum(int code, String statusEmail) {
        this.code = code;
        this.statusEmail = statusEmail;
    }

    public int getCode() {
        return code;
    }

    public String getStatusEmail() {
        return statusEmail;
    }
}
