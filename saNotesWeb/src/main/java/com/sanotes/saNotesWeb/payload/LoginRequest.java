package com.sanotes.saNotesWeb.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String emailOrUsername;

    @NotBlank
    private String password;

    public LoginRequest(){}

    public LoginRequest(String emailOrUsername,String password){
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }


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
