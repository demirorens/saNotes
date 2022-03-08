package com.sanotes.commons.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.TagModel;
import com.sanotes.commons.model.audit.DateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})
@JsonIdentityInfo(scope = User.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User extends DateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    @Size(max = 50)
    private String firstname;

    @NotBlank
    @Column(name = "last_name")
    @Size(max = 50)
    private String lastname;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteBookModel> noteBooks;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagModel> tags;

    public User(String firstname, String lastname, String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @JsonManagedReference
    public List<NoteBookModel> getNoteBooks() {
        return noteBooks == null ? null : new ArrayList<>(noteBooks);
    }

    public void setNoteBooks(List<NoteBookModel> noteBooks) {
        if (noteBooks == null) {
            this.noteBooks = null;
        } else {
            this.noteBooks = Collections.unmodifiableList(noteBooks);
        }
    }

    @JsonManagedReference
    public List<TagModel> getTags() {
        return tags == null ? null : new ArrayList<>(tags);
    }

    public void setTags(List<TagModel> tags) {
        if (tags == null) {
            this.tags = null;
        } else {
            this.tags = Collections.unmodifiableList(tags);
        }
    }

}
