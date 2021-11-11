package com.sanotes.saNotesPostgres.service.model;

import javax.persistence.*;

@Entity
@Table(name="notes",
        indexes = {@Index(name = "notes_index1",  columnList="note_id,notebook_id", unique = true)})
public class NotesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "note_id", nullable = false)
    private String noteId;

    @Column(name = "notebook_id", nullable = false)
    private long noteBookId;
}
