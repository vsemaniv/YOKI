package com.cusbee.yoki.model.binotel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinotelPostModel {
    @JsonProperty("key")
    private String key;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("ext_number")
    private String ext_number;

    @JsonProperty("phone_number")
    private String phone_number;

    public BinotelPostModel(String key, String signature, String ext_number, String phone_number) {
        this.key = key;
        this.signature = signature;
        this.ext_number = ext_number;
        this.phone_number = phone_number;
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

    public String getExt_number() {
        return ext_number;
    }

    public void setExt_number(String ext_number) {
        this.ext_number = ext_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
