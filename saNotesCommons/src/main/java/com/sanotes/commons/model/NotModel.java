package com.sanotes.commons.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "note")
public class NotModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String topic;
    private String text;

    public NotModel(String topic, String text) {

        this.topic = topic;
        this.text = text;
    }

}
