package com.sanotes.commons.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sanotes.commons.model.audit.UserAudit;
import com.sanotes.commons.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
@JsonIdentityInfo(scope = TagModel.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class TagModel extends UserAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    @JoinTable(name = "note_tags",
            joinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "note_id",referencedColumnName = "id"))
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

    @JsonBackReference
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
