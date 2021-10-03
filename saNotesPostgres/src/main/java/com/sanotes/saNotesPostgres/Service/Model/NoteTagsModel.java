package com.sanotes.saNotesPostgres.Service.Model;

import javax.persistence.*;

@Entity
@Table(name="note_tags",
        indexes = {@Index(name = "notestags_index1",  columnList="noteId,tagId", unique = true)})
public class NoteTagsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "note_id", nullable = false)
    private String noteId;

    @Column(name = "tag_id", nullable = false)
    private long tagId;

    public NoteTagsModel() {
    }

    public NoteTagsModel(String noteId, long tagId) {
        this.noteId = noteId;
        this.tagId = tagId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
