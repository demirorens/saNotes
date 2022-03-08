package com.sanotes.commons.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sanotes.commons.model.audit.UserAudit;
import com.sanotes.commons.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "notebook")
@JsonIdentityInfo(scope = NoteBookModel.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class NoteBookModel extends UserAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;


    @OneToMany(mappedBy = "notebook", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<NotesModel> notes;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public NoteBookModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public NoteBookModel(Long noteBookId) {
        this.id = noteBookId;
    }


    @JsonBackReference
    public User getUser() {
        return user;
    }

    @JsonManagedReference
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
