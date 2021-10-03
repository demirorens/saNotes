package com.sanotes.saNotesPostgres.Service.DAO;

import com.sanotes.saNotesPostgres.Service.Model.NoteTagsModel;
import org.springframework.data.repository.CrudRepository;

public interface NoteTagsRepository extends CrudRepository<NoteTagsModel, Long> {
}
