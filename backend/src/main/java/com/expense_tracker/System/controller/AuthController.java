package com.expense_tracker.System.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.System.config.JwtUtil;
import com.expense_tracker.System.dto.AuthRequest;
import com.expense_tracker.System.dto.AuthResponse;
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

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public String register(@RequestBody User user) {

		if (userRepository.existsByEmail(user.getEmail())) {

			throw new RuntimeException("This email is already registered");
		}

		user.setEmail(user.getEmail());
		user.setName(user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Role.ROLE_USER);
		userRepository.save(user);
		return "User Register Successfully!";

	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found!"));

		System.out.println("Input email: " + request.getEmail());
		System.out.println("DB email: " + user.getEmail());
		System.out.println("Password match: " +
				passwordEncoder.matches(request.getPassword(), user.getPassword()));
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid Password!");
		}

		String token = jwtUtil.generateToken(user);

		return ResponseEntity.ok(
				new AuthResponse(

						user.getEmail(),
						user.getName(),
						token));
	}

	@GetMapping("/users")
	public List<User> listUser() {
		return userRepository.findAll();
	}

}
