package com.cusbee.yoki.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */
@Component
public class CorsFilter implements Filter{
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException { 
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String token = req.getHeader("Authorization");
		res.setHeader("Access-Control-Allow-Origin", "*");
	    res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
	    res.setHeader("Access-Control-Max-Age", "3600");
<<<<<<< HEAD
	    res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, api_key, Authorization");
=======
	    res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	    res.setHeader("Authorization", token);
	    chain.doFilter(request, response);
	}

	@Override
	public void destroy() { }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException { }
	
}
