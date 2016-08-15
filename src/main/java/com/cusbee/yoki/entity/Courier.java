package com.cusbee.yoki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

//TODO remove
@Entity
@Table(name = "courier")
public class Courier implements BaseEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "account_id")
    //TODO HOW TO DO THIS?
    //@OneToOne(fetch = FetchType.LAZY, mappedBy = "courier")
    private Long accountId;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courier")
    @JsonIgnore
    private Set<Order> orders;

    @Column(name = "courier_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

    public enum CourierStatus {
        FREE, BUSY, OUT
    }
}
