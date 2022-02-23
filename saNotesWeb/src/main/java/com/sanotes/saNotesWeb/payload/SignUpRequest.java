package com.sanotes.saNotesWeb.payload;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

    @Schema(description = "First name",
            example = "Amedeus", required = true)
    @NotBlank
    @Size(min=2,max = 50)
    private String firstname;

    @Schema(description = "Last name",
            example = "Mozart", required = true)
    @NotBlank
    @Size(min=2,max = 50)
    private String lastname;

    @Schema(description = "User name",
            example = "amedeusmozart", required = true)
    @NotBlank
    @Size(min= 4,max = 50)
    private String username;

    @Schema(description = "Password",
            example = "Amedeus.mozart",required = true)
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @Schema(description = "Email",
            example = "amedeusmozart@gmail.com",required = true)
    @NotBlank
    @Size(min=4,max = 100)
    @Email
    private String email;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
