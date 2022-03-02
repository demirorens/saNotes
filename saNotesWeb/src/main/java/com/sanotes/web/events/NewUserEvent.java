package com.sanotes.web.events;

import com.sanotes.commons.model.user.User;
import org.springframework.context.ApplicationEvent;

public class NewUserEvent extends ApplicationEvent {

    public NewUserEvent(User source) {
        super(source);
    }

    public User getUser() {
        return (User) this.source;
    }

}
