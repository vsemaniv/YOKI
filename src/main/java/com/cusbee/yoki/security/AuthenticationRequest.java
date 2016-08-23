package com.cusbee.yoki.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cusbee.yoki.entity.Authority;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */
public class AuthenticationRequest extends ModelBase implements Serializable {

	private static final long serialVersionUID = 6624726180748515507L;
	private String username;
	private String password;
	private List<GrantedAuthority> roles;
	
	public AuthenticationRequest() {
		super();
	}

	public AuthenticationRequest(String username, String password, Authority role, Device device) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}
}
