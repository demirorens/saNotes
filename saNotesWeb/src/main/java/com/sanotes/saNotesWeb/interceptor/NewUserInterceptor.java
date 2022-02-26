package com.sanotes.saNotesWeb.interceptor;

import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.events.NewUserEvent;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class NewUserInterceptor extends EmptyInterceptor {
    private final ApplicationEventPublisher applicationEventPublisher;

    public NewUserInterceptor(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            applicationEventPublisher.publishEvent(new NewUserEvent((User) entity));
        }
        return true;
    }

}
