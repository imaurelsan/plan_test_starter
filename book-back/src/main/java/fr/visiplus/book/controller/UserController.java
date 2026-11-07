package fr.visiplus.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.visiplus.book.dto.LoginRequest;
import fr.visiplus.book.dto.RegisterRequest;
import fr.visiplus.book.dto.UserDTO;
import fr.visiplus.book.service.UserService;

@RestController
public class UserController {
	
	private UserService userService;
	
	public UserController(final UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody LoginRequest login) {
		return ResponseEntity.of(userService.login(login));		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest register) {
		return ResponseEntity.ok(userService.register(register));
	}

}
