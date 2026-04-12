package com.example.Project_With_AI.auth.service.impl;

import com.example.Project_With_AI.auth.dto.AuthResponse;
import com.example.Project_With_AI.auth.dto.LoginRequest;
import com.example.Project_With_AI.auth.dto.RegisterRequest;
import com.example.Project_With_AI.auth.entity.Role;
import com.example.Project_With_AI.auth.entity.User;
import com.example.Project_With_AI.auth.repository.UserRepository;
import com.example.Project_With_AI.auth.security.JwtService;
import com.example.Project_With_AI.auth.service.AuthService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public AuthResponse register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email is already in use");
		}

		User user = User.builder()
			.fullName(request.fullName())
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.role(Role.USER)
			.build();

		User savedUser = userRepository.save(user);
		String jwtToken = jwtService.generateToken(savedUser, Map.of("role", savedUser.getRole().name()));

		return new AuthResponse(
			jwtToken,
			"Bearer",
			jwtService.getExpirationMs(),
			savedUser.getEmail(),
			savedUser.getRole()
		);
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
			);
		} catch (AuthenticationException exception) {
			throw new IllegalArgumentException("Invalid email or password");
		}

		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		String jwtToken = jwtService.generateToken(user, Map.of("role", user.getRole().name()));
		return new AuthResponse(
			jwtToken,
			"Bearer",
			jwtService.getExpirationMs(),
			user.getEmail(),
			user.getRole()
		);
	}
}
