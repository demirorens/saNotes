package com.sanotes.web.payload;

public class ByIdRequest {
    private Long id;

    public ByIdRequest() {
    }

    public ByIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
