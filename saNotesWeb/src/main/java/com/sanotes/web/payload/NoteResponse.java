package com.sanotes.web.payload;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public NoteResponse() {
    }

    public NoteResponse(Long id, String noteId, String topic, String text,NoteBookResponse notebook, List<TagResponse> tags) {
        this.id = id;
        this.noteId = noteId;
        this.topic = topic;
        this.text = text;
        setNotebook(notebook);
        setTags(tags);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TagResponse> getTags() {
        return tags == null ? null : new ArrayList<>(tags);
    }

    public void setTags(List<TagResponse> tags) {
        if(tags == null)
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
