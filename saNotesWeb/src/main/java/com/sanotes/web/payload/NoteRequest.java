package com.sanotes.web.payload;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class NoteRequest {

    private Long id;
    private String noteId;
    @NotBlank
    private String topic;
    @NotBlank
    private String text;

    private NoteBookRequest notebook;
    private List<TagRequest> tags;

    public NoteRequest() {
    }

    public NoteRequest(Long noteBookId, String topic, String text) {
        this.topic = topic;
        this.text = text;
        this.notebook = new NoteBookRequest(noteBookId);
    }

    public NoteRequest(String topic, String text, NoteBookRequest notebook, List<TagRequest> tags) {
        this.topic = topic;
        this.text = text;
        this.notebook = notebook;
        this.tags = tags;
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

    public NoteBookRequest getNotebook() {
        return notebook;
    }

    public void setNotebook(NoteBookRequest notebook) {
        this.notebook = notebook;
    }

    public List<TagRequest> getTags() {
        return tags;
    }

    public void setTags(List<TagRequest> tags) {
        this.tags = tags;
    }
}
