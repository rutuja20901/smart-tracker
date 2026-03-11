package com.expense_tracker.System.dto;

public class AuthResponse {

	private String email;
	private String name;
	private String token;
	public AuthResponse() {
		super();
	}
	public AuthResponse(String email, String name, String token) {
		super();
		this.email = email;
		this.name = name;
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "AuthResponse [email=" + email + ", name=" + name + ", token=" + token + "]";
	}
	
	
	
}
