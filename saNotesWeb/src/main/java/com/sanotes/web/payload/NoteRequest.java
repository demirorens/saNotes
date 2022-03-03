package com.sanotes.web.payload;

import javax.validation.constraints.NotBlank;

public class NoteRequest {

    private Long id;
    private String noteId;
    @NotBlank
    private String topic;
    @NotBlank
    private String text;

    public NoteRequest() {
    }

    public NoteRequest(String topic, String text) {
        this.topic = topic;
        this.text = text;
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


}
