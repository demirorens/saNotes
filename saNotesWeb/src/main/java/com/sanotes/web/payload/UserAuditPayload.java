package com.sanotes.web.payload;

public abstract class UserAuditPayload extends DateAuditPayload {
    private long createdBy;
    private long lastModifiedBy;

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
