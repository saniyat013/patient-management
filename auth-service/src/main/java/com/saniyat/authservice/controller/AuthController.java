package com.saniyat.authservice.controller;

import com.saniyat.authservice.dto.LoginRequestDTO;
import com.saniyat.authservice.dto.LoginResponseDTO;
import com.saniyat.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@Operation(summary = "Authenticate user and return JWT token")
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

		if (tokenOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String token = tokenOptional.get();
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
}
