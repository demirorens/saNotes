package com.sanotes.mongo.repository;

import com.sanotes.commons.model.NotModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<NotModel, String> {
    List<NotModel> findByTopicContaining(String topic);
    List<NotModel> findByTextContaining(String text);
}
