package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

import com.cusbee.yoki.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "orders")
@Entity
public class Order implements BaseEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishQuantity> dishes = new ArrayList<>();

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar orderDate;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "cost")
    private Double cost;

    //shows if order is actual for courier.
    @Column
    private Boolean pending = Boolean.FALSE;

    @Column(name = "written_off")
    private Boolean writtenOff = Boolean.FALSE;

    @ManyToOne(cascade = CascadeType.ALL)
    private CourierDetails courierDetails;

    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    @Column(name = "message")
    private String message;
    
    @Column(name = "time_to_take")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeToTake;
    
    @Column(name = "time_taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeTaken;
    
    @Column(name = "time_to_deliver")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeToDeliver;
    
    @Column(name = "time_delivered")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeDelivered;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public Calendar getTimeToTake() {
		return timeToTake;
	}

	public void setTimeToTake(Calendar timeToTake) {
		this.timeToTake = timeToTake;
	}

	public Calendar getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Calendar timeTaken) {
		this.timeTaken = timeTaken;
	}

	public Calendar getTimeToDeliver() {
		return timeToDeliver;
	}

	public void setTimeToDeliver(Calendar timeToDeliver) {
		this.timeToDeliver = timeToDeliver;
	}

	public Calendar getTimeDelivered() {
		return timeDelivered;
	}

	public void setTimeDelivered(Calendar timeDelivered) {
		this.timeDelivered = timeDelivered;
	}

    public CourierDetails getCourierDetails() {
        return courierDetails;
    }

    public void setCourierDetails(CourierDetails courierDetails) {
        this.courierDetails = courierDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public List<DishQuantity> getDishes() {
        return dishes;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isWrittenOff() {
        return writtenOff;
    }

    public void setWrittenOff(boolean writtenOff) {
        this.writtenOff = writtenOff;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
