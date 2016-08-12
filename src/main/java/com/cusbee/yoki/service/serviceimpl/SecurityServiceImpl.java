package com.cusbee.yoki.service.serviceimpl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Override
	public Boolean hasProtectedAccess() {
		return (SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));
	}

}
