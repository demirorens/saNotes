package com.sanotes.web.payload;

import javax.validation.constraints.NotBlank;


public class NoteBookRequest {


    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public NoteBookRequest() {
    }

    public NoteBookRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
