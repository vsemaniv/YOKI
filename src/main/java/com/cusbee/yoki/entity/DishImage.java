package com.cusbee.yoki.entity;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

<<<<<<< HEAD
import java.io.Serializable;

import javax.persistence.*;

@Table(name = "dish_image")
@Entity
public class DishImage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @Fetch(FetchMode.JOIN)
    @JsonBackReference
    private Dish dish;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Dish getDish() {
        return this.dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
=======
=======
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import java.io.Serializable;

import javax.persistence.*;

@Table(name = "dish_image")
@Entity
public class DishImage implements Serializable {

<<<<<<< HEAD
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String location;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @Fetch(FetchMode.JOIN)
    @JsonBackReference
    private Dish dish;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Dish getDish() {
        return this.dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
