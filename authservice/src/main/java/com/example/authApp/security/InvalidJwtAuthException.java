package com.example.authApp.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthException extends AuthenticationException{
	
	private static final long serialVersionUID = -761503632186596342L;

	public InvalidJwtAuthException(String e) {
        super(e);
    }

}
