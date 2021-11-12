package com.sanotes.saNotesPostgres.service.model.audit;

import javax.persistence.MappedSuperclass;

@MappedSuperclass

public class DateAudit {

    private long createdBy;
    private long lastModifiedBy;
}
