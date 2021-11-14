package com.sanotes.saNotesWeb.payload;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteResponse {

    private Long id;
    private String noteId;
    private String topic;
    private String text;
    private List<String> tags;

    public NoteResponse() {
    }

    public NoteResponse(Long id, String noteId, String topic, String text, List<String> tags) {
        this.id = id;
        this.noteId = noteId;
        this.topic = topic;
        this.text = text;
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

    public List<String> getTags() {
        return tags == null ? null : new ArrayList<>(tags);
    }

    public void setTags(List<String> tags) {
        if(tags == null)
            this.tags = null;
        else
            this.tags = Collections.unmodifiableList(tags);
    }
}
