package com.sanotes.web.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class TagRequest {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public TagRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TagRequest(Long id, String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

}
