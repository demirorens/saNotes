package com.sanotes.saNotesWeb.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagResponse {
    private Long id;
    private String name;
    private String description;
    private List<NoteResponse> notes;

    public TagResponse() {
    }

    public TagResponse(Long id, String name, String description, List<NoteResponse> notes) {
        this.id = id;
        this.name = name;
        this.description = description;
        setNotes(notes);
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

    @JsonIgnore
    public List<NoteResponse> getNotes() {
        return notes == null ? null: new ArrayList<>(notes);
    }

    public void setNotes(List<NoteResponse> notes) {
        if(notes == null)
            this.notes = null;
        else
            this.notes = Collections.unmodifiableList(notes);
    }
}
