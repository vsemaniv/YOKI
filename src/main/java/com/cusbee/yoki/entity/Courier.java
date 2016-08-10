package com.cusbee.yoki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@Entity
@Table(name = "courier")
public class Courier implements Activatable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    @Type(type = "org.hibernate.type.YesNoType")
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courier")
    @JsonIgnore
    private Set<Order> orders;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar assignmentTime;

    @Column
    private CourierStatus status;

    @Override
    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

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
