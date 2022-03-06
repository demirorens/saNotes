package com.sanotes.commons.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sanotes.commons.model.audit.UserAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "notes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"note_id"})})
@JsonIdentityInfo(scope = NotesModel.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class NotesModel extends UserAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "note_id", nullable = false)
    @NaturalId(mutable = true)
    private String noteId;

    @Transient
    private String topic;
    @Transient
    private String text;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook_id")
    private NoteBookModel notebook;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "note_tags",
            joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<TagModel> tags;

    public NotesModel() {
    }

    public NotesModel(Long id, String noteId, String topic, String text, NoteBookModel notebook) {
        this.id = id;
        this.noteId = noteId;
        this.topic = topic;
        this.text = text;
        this.notebook = notebook;
    }

    public NotesModel(String topic, String text) {
        this.topic = topic;
        this.text = text;
    }

    public NotesModel(Long id, String topic, String text) {
        this.topic = topic;
        this.text = text;
        this.id = id;
    }

    @JsonBackReference
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
