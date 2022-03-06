package com.sanotes.web.payload;

import javax.validation.constraints.NotBlank;

public class TagRequest {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public TagRequest() {
    }

    public TagRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TagRequest(Long id, String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}
