package com.sanotes.saNotesWeb.events;

import com.sanotes.saNotesCommons.model.user.User;
import org.springframework.context.ApplicationEvent;

public class NewUserEvent extends ApplicationEvent {

    public NewUserEvent(User source) {
        super(source);
    }

    public User getUser() {
        return (User) this.source;
    }

}
