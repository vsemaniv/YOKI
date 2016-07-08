package com.cusbee.yoki.config;

import java.io.IOException;
import com.cusbee.yoki.utils.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CorsFilter implements Filter{
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException { 
		HttpServletResponse res = (HttpServletResponse) response;
		MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest((HttpServletRequest)request);
		String token = mutableRequest.getHeader("Authorization");
		res.setHeader("Access-Control-Allow-Origin", "*");
	    res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
	    res.setHeader("Access-Control-Max-Age", "3600");
	    res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	    mutableRequest.addHeader("Authorization", token);
	    chain.doFilter(mutableRequest, response);
	}

	@Override
	public void destroy() { }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException { }
	
}
