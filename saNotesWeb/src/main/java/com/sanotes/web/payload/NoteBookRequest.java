package com.sanotes.web.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class NoteBookRequest {


    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public NoteBookRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public NoteBookRequest(Long noteBookId) {
        this.id = noteBookId;
    }
}
