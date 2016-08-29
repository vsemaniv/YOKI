package com.cusbee.yoki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

//TODO remove
@Entity
@Table(name = "courier_details")
public class CourierDetails implements BaseEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courierDetails")
    @JsonIgnore
    private Set<Order> orders;

    @Column(name = "courier_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    @Column(name = "messaging_token")
    private String messagingToken;

    public CourierDetails() {
    }

    public CourierDetails(String messagingToken, CourierStatus status, Account account) {
        this.messagingToken = messagingToken;
        this.status = status;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

    public String getMessagingToken() {
        return messagingToken;
    }

    public void setMessagingToken(String messagingToken) {
        this.messagingToken = messagingToken;
    }

    public enum CourierStatus {
        FREE, BUSY, OUT
    }
}
