package com.sanotes.saNotesPostgres.service.DAO;

import com.sanotes.saNotesPostgres.service.model.NoteTagsModel;
import org.springframework.data.repository.CrudRepository;

public interface NoteTagsRepository extends CrudRepository<NoteTagsModel, Long> {
}
