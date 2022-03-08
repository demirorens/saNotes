package com.sanotes.commons.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sanotes.commons.model.audit.UserAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
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

    public NotesVersionModel(NotesModel notesModel) {
        this.id = notesModel.getId();
        this.noteId = notesModel.getNoteId();
        this.topic = notesModel.getTopic();
        this.text = notesModel.getText();
        this.notebook = notesModel.getNotebook();

    }

    @JsonBackReference
    public NoteBookModel getNotebook() {
        return notebook;
    }

}
