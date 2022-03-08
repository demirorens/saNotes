package com.sanotes.web.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TagResponse {
    private Long id;
    private String name;
    private String description;
    private List<NoteResponse> notes;

    public TagResponse(Long id, String name, String description, List<NoteResponse> notes) {
        this.id = id;
        this.name = name;
        this.description = description;
        setNotes(notes);
    }

    @JsonIgnore
    public List<NoteResponse> getNotes() {
        return notes == null ? null : new ArrayList<>(notes);
    }

    public void setNotes(List<NoteResponse> notes) {
        if (notes == null)
            this.notes = null;
        else
            this.notes = Collections.unmodifiableList(notes);
    }
}
