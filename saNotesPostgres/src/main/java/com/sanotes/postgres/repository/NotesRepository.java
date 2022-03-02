package com.sanotes.postgres.repository;

import com.sanotes.commons.model.NotesModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NotesRepository extends CrudRepository<NotesModel, Long> {
    List<NotesModel> findByTags_Id(Long id);
    List<NotesModel> findByNotebook_Id(Long id);


}
