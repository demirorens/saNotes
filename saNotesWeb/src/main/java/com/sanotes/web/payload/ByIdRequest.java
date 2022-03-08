package com.sanotes.web.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ByIdRequest {
    private Long id;

    public ByIdRequest(Long id) {
        this.id = id;
    }

}
