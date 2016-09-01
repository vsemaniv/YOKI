package com.cusbee.yoki.model.binotel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinotelPostModel {
    @JsonProperty("key")
    private String key;

    @JsonProperty("signature")
    private String signature;

    public BinotelPostModel(String key, String signature) {
        this.key = key;
        this.signature = signature;
    }

    public BinotelPostModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
