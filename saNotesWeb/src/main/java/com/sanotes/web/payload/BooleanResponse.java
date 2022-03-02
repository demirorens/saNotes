package com.sanotes.web.payload;

public class BooleanResponse {
    private Boolean result;

    public BooleanResponse(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
