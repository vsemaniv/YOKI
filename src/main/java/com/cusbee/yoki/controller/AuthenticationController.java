package com.cusbee.yoki.controller;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cusbee.yoki.security.AuthenticationRequest;
import com.cusbee.yoki.security.AuthenticationResponse;
import com.cusbee.yoki.security.TokenUtils;
import com.cusbee.yoki.security.YokiUser;

@Controller
@RequestMapping("${yoki.route.authentication}")
public class AuthenticationController {
	
	@Value("${yoki.token.header}")
	private String tokenHeader;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest, Device device) throws AuthenticationException{
		
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
					)
				); 
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername()); 
		String token = this.tokenUtils.generateToken(userDetails, device);
		
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@RequestMapping(value = "${yoki.route.authentication.refresh}", method = RequestMethod.GET)
	public ResponseEntity<?> authenticationResponse(HttpServletRequest request) {
		
		String token = request.getHeader(this.tokenHeader);
		String username = this.tokenUtils.getUsernameFromToken(token);
		YokiUser user = (YokiUser) this.userDetailsService.loadUserByUsername(username);
		if(this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())){
			String refreshToken = this.tokenUtils.refreshToken(token);
			return ResponseEntity.ok(new AuthenticationResponse(refreshToken));
		}else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
