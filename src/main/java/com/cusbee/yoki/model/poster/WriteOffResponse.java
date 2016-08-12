package com.cusbee.yoki.model.poster;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 11.08.2016.
 */
public class WriteOffResponse {

    @JsonProperty("success")
    private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
