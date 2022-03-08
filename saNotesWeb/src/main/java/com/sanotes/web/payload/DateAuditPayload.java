package com.sanotes.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public abstract class DateAuditPayload {
    private Instant createdAt;
    private Instant lastModifiedAt;

}
