package com.sanotes.saNotesPostgres.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanotes.saNotesPostgres.service.model.audit.UserAudit;
import com.sanotes.saNotesPostgres.service.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
public class TagModel extends UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(name = "note_tags",
            joinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "note_id",referencedColumnName = "note_id"))
    private List<NotesModel> notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public TagModel() {
    }

    public TagModel(String name, String description) {
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

    public List<NotesModel> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesModel> notes) {
        this.notes = notes;
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
}
