package com.expense_tracker.System.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.System.config.JwtUtil;
import com.expense_tracker.System.dto.AuthRequest;
import com.expense_tracker.System.entity.Role;
import com.expense_tracker.System.entity.User;
import com.expense_tracker.System.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		user.setEmail(user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Role.ROLE_USER);
		userRepository.save(user);
		return "User Register Successfully!";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody AuthRequest request) {
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found!"));
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
			throw new RuntimeException("Invalid Password!");
		}
		
		return jwtUtil.generateToken(user.getEmail());
	}

}
