package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
<<<<<<< HEAD
@Table(name = "client")
public class Client implements BaseEntity, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "name", length = 35, nullable = false)
    private String name;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonIgnore
    private List<Order> order;

    
    public Long getId() {
=======
@Table(name="client")
public class Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="first_name", length=35, nullable=false)
	private String firstName;
	
	@Column(name="phone_number", length=35, nullable=false)
	private String phoneNumber;
	
	@Column(name="location", length=35, nullable=false)
	private String location;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="client")
	@JsonIgnore
	private List<Order> order;

	public Long getId() {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

<<<<<<< HEAD
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

=======
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
}
