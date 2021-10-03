package com.sanotes.saNotesMongo.DAO;

import com.sanotes.saNotesMongo.Model.NoteModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<NoteModel, String> {
    List<NoteModel> findByTopicContaining(String topic);
    List<NoteModel> findByTextContaining(String text);
}
