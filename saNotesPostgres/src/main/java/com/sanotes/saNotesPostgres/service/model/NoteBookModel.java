package com.sanotes.saNotesPostgres.service.model;

import javax.persistence.*;

@Entity
@Table(name = "notebook")
public class NoteBookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

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
