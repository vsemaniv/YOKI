package com.cusbee.yoki.model;

import com.cusbee.yoki.entity.Account;

import java.io.Serializable;

public class CourierModel implements RequestModel, Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Account account;
    private String messagingToken;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessagingToken() {
        return messagingToken;
    }

    public void setMessagingToken(String messagingToken) {
        this.messagingToken = messagingToken;
    }
}
