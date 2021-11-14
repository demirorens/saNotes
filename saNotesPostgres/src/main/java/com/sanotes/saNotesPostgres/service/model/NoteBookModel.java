package com.sanotes.saNotesPostgres.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanotes.saNotesPostgres.service.model.audit.DateAudit;
import com.sanotes.saNotesPostgres.service.model.audit.UserAudit;
import com.sanotes.saNotesPostgres.service.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "notebook")
public class NoteBookModel extends UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotesModel> notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public NoteBookModel() {
    }

    public NoteBookModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "NoteBookModel{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<NotesModel> getNotes() {
        return notes == null ? null : new ArrayList<>(notes);
    }

    public void setNotes(List<NotesModel> notes) {
        if (notes == null) {
            this.notes = null;
        } else {
            this.notes = Collections.unmodifiableList(notes);
        }
    }

}
