package com.cusbee.yoki.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */
public class AuthenticationTokenFilter extends
		UsernamePasswordAuthenticationFilter {

	private final static String TOKEN_HEADER = "Authorization";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenUtils tokenUtils;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		String username = this.tokenUtils.getUsernameFromToken(authToken);

		if (username != null
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService
					.loadUserByUsername(username);
			if (this.tokenUtils.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(
						authentication);
			}
		}

		chain.doFilter(request, response);
	}

}
