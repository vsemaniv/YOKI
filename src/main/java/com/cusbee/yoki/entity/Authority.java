package com.cusbee.yoki.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.AuthorityName;
=======
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */

@Table(name="authority")
@Entity
public class Authority {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name", length=50)
	@Enumerated(EnumType.STRING)
	private AuthorityName name;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="authorities")
	@JsonIgnore
	private List<Account> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthorityName getName() {
		return name;
	}

	public void setName(AuthorityName name) {
		this.name = name;
	}

	public List<Account> getUsers() {
		return users;
	}

	public void setUsers(List<Account> users) {
		this.users = users;
	}
	
}
