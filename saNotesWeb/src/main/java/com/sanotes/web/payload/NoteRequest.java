package com.sanotes.web.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class NoteRequest {

    private Long id;
    private String noteId;
    @NotBlank
    private String topic;
    @NotBlank
    private String text;

    private NoteBookRequest notebook;
    private List<TagRequest> tags;

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

}
