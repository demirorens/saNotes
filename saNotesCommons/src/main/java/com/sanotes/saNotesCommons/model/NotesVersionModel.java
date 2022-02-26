package com.sanotes.saNotesCommons.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sanotes.saNotesCommons.model.audit.UserAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "notes_version",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "note_id"})})
@JsonIdentityInfo(scope = NotesVersionModel.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class NotesVersionModel extends UserAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long version_id;

    @Column(name = "id", nullable = false)
    @NaturalId
    private Long id;

    @Column(name = "note_id", nullable = false)
    @NaturalId
    private String noteId;

    @Transient
    private String topic;
    @Transient
    private String text;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook_id")
    private NoteBookModel notebook;


    public NotesVersionModel() {
    }

    public Long getVersion_id() {
        return version_id;
    }

    public void setVersion_id(Long version_id) {
        this.version_id = version_id;
    }

    @JsonBackReference
    public NoteBookModel getNotebook() {
        return notebook;
    }

    public void setNotebook(NoteBookModel notebook) {
        this.notebook = notebook;
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
