package com.sanotes.web.payload;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private List<NoteBookResponse> notebooks;
    private List<TagResponse> tags;

    public UserResponse() {
    }

    public UserResponse(Long id, String firstname, String lastname, String username, String email, List<NoteBookResponse> notebooks, List<TagResponse> tags) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        setNotebooks(notebooks);
        setTags(tags);
    }


    public List<NoteBookResponse> getNotebooks() {
        return notebooks == null ? null: new ArrayList<>(notebooks);
    }

    public void setNotebooks(List<NoteBookResponse> notebooks) {
        if(notebooks == null)
            this.notebooks = null;
        else
            this.notebooks = Collections.unmodifiableList(notebooks);
    }


    public List<TagResponse> getTags() {
        return tags == null ? null: new ArrayList<>(tags);
    }

    public void setTags(List<TagResponse> tags) {
        if(tags == null)
            this.tags = null;
        else
            this.tags = Collections.unmodifiableList(tags);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
