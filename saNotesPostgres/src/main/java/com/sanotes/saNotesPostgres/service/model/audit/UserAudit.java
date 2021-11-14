package com.sanotes.saNotesPostgres.service.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy","lastModifiedBy"},
        allowGetters = true
)
public abstract class UserAudit extends DateAudit{
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column( nullable = false,updatable = false)
    private long createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    private long lastModifiedBy;

    public UserAudit() {
        super();
    }

    public UserAudit( long createdBy, long lastModifiedBy) {
        super();
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
