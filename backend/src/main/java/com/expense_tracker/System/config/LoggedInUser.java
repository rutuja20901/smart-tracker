package com.expense_tracker.System.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.expense_tracker.System.entity.User;
import com.expense_tracker.System.repository.UserRepository;

@Component
public class LoggedInUser {
	

	@Autowired
	private UserRepository userRepository;
	
	public User getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Authentication**" + auth);
		System.out.println(auth.getAuthorities());
		String username = auth.getName();
		
		System.out.println("Username is: " + username);
		return userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found!"));
	}

}
