package com.sanotes.saNotesWeb.interceptor;

import com.sanotes.saNotesCommons.model.NotModel;
import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
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
    private NoteRepository noteRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_LOAD).appendListener(this);
    }

    @Override
    public void onPostLoad(PostLoadEvent postLoadEvent) {
        final Object entity = postLoadEvent.getEntity();
        if (entity instanceof NotesModel) {
            if (((NotesModel) entity).getNoteId() != null &&
                    (((NotesModel) entity).getTopic() == null && ((NotesModel) entity).getText() == null)
            ) {
                NotModel notModel = noteRepository.findById(((NotesModel) entity).getNoteId())
                        .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", ((NotesModel) entity).getNoteId()));
                ((NotesModel) entity).setTopic(notModel.getTopic());
                ((NotesModel) entity).setText(notModel.getText());
            }
            System.out.println("NotesModel");
        }
    }

}
