package com.petHosting.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final Collection<? extends GrantedAuthority> roles;
	public JwtResponse(String jwttoken, Collection<? extends GrantedAuthority> roles) {
		this.jwttoken = jwttoken;
		this.roles = roles;
	}

	public String getToken() {
		return this.jwttoken;
	}


	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}
}