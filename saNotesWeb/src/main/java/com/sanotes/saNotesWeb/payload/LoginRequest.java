package com.sanotes.saNotesWeb.payload;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @Schema(description = "eMail or Username",
            example = "amedeusmozart@gmail.com", required = true)
    @NotBlank
    private String emailOrUsername;

    @Schema(description = "User Password",
            example = "Amedeus.mozart",required = true)
    @NotBlank
    private String password;

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
