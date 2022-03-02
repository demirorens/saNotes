package com.sanotes.commons.model.audit;

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
    @Column( updatable = false)
    private Long createdBy;

    @LastModifiedBy
    private Long lastModifiedBy;

    protected UserAudit() {
        super();
    }

    protected UserAudit(Long createdBy, Long lastModifiedBy) {
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
