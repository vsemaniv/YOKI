package com.cusbee.yoki.entity;

import java.util.List;

import javax.persistence.*;

import com.cusbee.yoki.entity.enums.AuthorityName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */

@Table(name="authority")
@Entity
public class Authority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name", length=50)
	@Enumerated(EnumType.STRING)
	private AuthorityName name;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="authorities", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Account> users;

	public Authority() {
	}

	public Authority(AuthorityName name) {
		this.name = name;
	}

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


	@Override
	public String getAuthority() {
		return name.name();
	}
}
