package com.example.authApp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.authApp.web.RestExceptionHandler;


public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

	Logger log=LoggerFactory.getLogger(RestExceptionHandler.class);
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.debug("Jwt authentication failed:" + authException);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED	, "Jwt authentication failed");

	}
}