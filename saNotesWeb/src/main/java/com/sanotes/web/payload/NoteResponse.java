package com.sanotes.web.payload;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonIdentityInfo(scope = NoteResponse.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class NoteResponse {

    private Long id;
    private String noteId;
    private String topic;
    private String text;
    private NoteBookResponse notebook;
    private List<TagResponse> tags;

    public NoteResponse(Long id, String noteId, String topic, String text, NoteBookResponse notebook, List<TagResponse> tags) {
        this.id = id;
        this.noteId = noteId;
        this.topic = topic;
        this.text = text;
        setNotebook(notebook);
        setTags(tags);
    }


    public List<TagResponse> getTags() {
        return tags == null ? null : new ArrayList<>(tags);
    }

    public void setTags(List<TagResponse> tags) {
        if (tags == null)
            this.tags = null;
        else
            this.tags = Collections.unmodifiableList(tags);
    }

    @JsonBackReference
    public NoteBookResponse getNotebook() {
        return notebook;
    }

    public void setNotebook(NoteBookResponse notebook) {
        this.notebook = notebook;
    }
}
