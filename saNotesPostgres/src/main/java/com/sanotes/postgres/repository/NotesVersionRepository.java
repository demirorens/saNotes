package com.sanotes.postgres.repository;

import com.sanotes.commons.model.NotesVersionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;


public interface NotesVersionRepository extends CrudRepository<NotesVersionModel, Long> {
    @Query("select n from NotesVersionModel n where n.id = ?1")
    List<NotesVersionModel> getVersionsByNotesId(@NonNull Long id);

}
