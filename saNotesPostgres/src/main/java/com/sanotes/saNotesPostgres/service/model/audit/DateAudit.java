package com.sanotes.saNotesPostgres.service.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt","lastModifiedAt"},
        allowGetters = true
)
public abstract class DateAudit {
    private static final long serialVersionUID = 1L;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private long createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private long lastModifiedAt;

    public DateAudit() {
    }

    public DateAudit(long createdAt, long lastModifiedAt) {
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }


}
