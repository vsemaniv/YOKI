package com.cusbee.yoki.model;

import java.util.Collection;
import java.util.Date;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
=======
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;


<<<<<<< HEAD
public class AccountModel implements RequestModel {

	private Long id;
	private String username;
	private String oldPassword;
	private String newPassword;
=======
public class AccountModel {

	private Long id;
	private String username;
	private String password;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	private String email;
	@JsonIgnore
	private Date lastPasswordReset;
	private String firstname;
	private String lastname;
	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;
	@JsonIgnore
	private Boolean accountNonExpired;
	@JsonIgnore
	private Boolean accountNonLocked;
	@JsonIgnore
	private Boolean credentialsNonExpired = true;
	@JsonIgnore
	private Boolean enabled;
	private String authority;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

<<<<<<< HEAD
	@JsonIgnore
	public String getOldPassword() {
		return oldPassword;
	}

	@JsonProperty
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
=======
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastPasswordReset() {
		return lastPasswordReset;
	}

	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(
			Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
