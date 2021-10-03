package com.sanotes.saNotesPostgres.Service.Model;

import javax.persistence.*;

@Entity
@Table(name="notes",
        indexes = {@Index(name = "notes_index1",  columnList="noteId,noteBookId", unique = true)})
public class NotesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "note_id", nullable = false)
    private String noteId;

    @Column(name = "notebook_id", nullable = false)
    private long noteBookId;
}
