package com.cusbee.yoki.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.security.AuthenticationRequest;
import com.cusbee.yoki.security.AuthenticationResponse;
import com.cusbee.yoki.security.TokenUtils;
import com.cusbee.yoki.security.YokiUser;
import com.cusbee.yoki.utils.MutableHttpServletRequest;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.mangofactory.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
	
	private final static String TOKEN_HEADER = "Authorization";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@ApiIgnore
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity authenticationRequest(@ApiModel(type = AuthenticationRequest.class, collection = false) @RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response, HttpServletRequest request){
		
		MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
 		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
					)
				); 
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername()); 
		String token = this.tokenUtils.generateToken(userDetails);
		response.setHeader(TOKEN_HEADER, token);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@ApiIgnore
	@RequestMapping(value = "refresh", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public ResponseEntity authenticationResponse(HttpServletRequest request) {
		
		String token = request.getHeader(TOKEN_HEADER);
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
