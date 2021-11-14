package com.sanotes.saNotesPostgres.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanotes.saNotesPostgres.service.model.audit.DateAudit;
import com.sanotes.saNotesPostgres.service.model.audit.UserAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="notes",
        uniqueConstraints = {@UniqueConstraint(name = "note_id",columnNames = {"note_id"})})
public class NotesModel extends UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "note_id", nullable = false)
    @NaturalId
    private String noteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook_id")
    private NoteBookModel notebook;

    @ManyToMany(mappedBy = "notes")
    private List<TagModel> tags;

    public NotesModel() {
    }

    public NotesModel(String noteId) {
        this.noteId = noteId;
    }

    public NoteBookModel getNotebook() {
        return notebook;
    }

    public void setNotebook(NoteBookModel notebook) {
        this.notebook = notebook;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
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

}
