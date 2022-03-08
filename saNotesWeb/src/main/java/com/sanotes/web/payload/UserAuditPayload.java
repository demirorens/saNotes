package com.sanotes.web.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class UserAuditPayload extends DateAuditPayload {
    private long createdBy;
    private long lastModifiedBy;

}
