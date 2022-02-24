package com.sanotes.saNotesWeb.interceptor;

import com.sanotes.saNotesCommons.model.NotModel;
import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class NoteInterceptor extends EmptyInterceptor {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types){
        /*if (entity instanceof NotesModel) {
            if(((NotesModel) entity).getTopic().isEmpty() && ((NotesModel) entity).getText().isEmpty()){
                NotModel notModel = noteRepository.findById(((NotesModel) entity).getNoteId())
                        .orElseThrow(()-> new ResourceNotFoundException("Note", "by id",((NotesModel) entity).getNoteId()));
                ((NotesModel) entity).setTopic(notModel.getTopic());
                ((NotesModel) entity).setText(notModel.getText());
            }
            System.out.println("NotesModel");
        }*/
        return true;
    }

}
