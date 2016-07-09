package com.cusbee.yoki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.repositories.UserRepository;
import com.cusbee.yoki.utils.UserFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account user = userRepository.findByUsername(username);
		if(user==null){
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return UserFactory.create(user);
		}
	}
}
