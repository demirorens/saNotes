package com.sanotes.web.interceptor;

import com.sanotes.commons.model.NotModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.NotesVersionModel;
import com.sanotes.mongo.repository.NoteRepository;
import com.sanotes.web.exception.ResourceNotFoundException;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Component
public class NoteLoadEventListener implements PostLoadEventListener {

    @Autowired
    private transient NoteRepository noteRepository;

    @Autowired
    private transient EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_LOAD).appendListener(this);
    }

    @Override
    public void onPostLoad(PostLoadEvent postLoadEvent) {
        final Object entity = postLoadEvent.getEntity();
        if (entity instanceof NotesModel
                && ((NotesModel) entity).getNoteId() != null
                && ((NotesModel) entity).getTopic() == null
                && ((NotesModel) entity).getText() == null) {
            NotModel notModel = noteRepository.findById(((NotesModel) entity).getNoteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", ((NotesModel) entity).getNoteId()));
            ((NotesModel) entity).setTopic(notModel.getTopic());
            ((NotesModel) entity).setText(notModel.getText());

        } else if (entity instanceof NotesVersionModel
                && ((NotesVersionModel) entity).getNoteId() != null
                && ((NotesVersionModel) entity).getTopic() == null
                && ((NotesVersionModel) entity).getText() == null) {
            NotModel notModel = noteRepository.findById(((NotesVersionModel) entity).getNoteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", ((NotesVersionModel) entity).getNoteId()));
            ((NotesVersionModel) entity).setTopic(notModel.getTopic());
            ((NotesVersionModel) entity).setText(notModel.getText());

        }
    }

}
