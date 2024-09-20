package br.com.plgs.AppClientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.plgs.AppClientes.configuration.JwtTokenUtil;
import br.com.plgs.AppClientes.dto.LoginUserDto;
import br.com.plgs.AppClientes.dto.RegisterUserDto;
import br.com.plgs.AppClientes.model.User;
import br.com.plgs.AppClientes.service.AuthService;
import br.com.plgs.AppClientes.utils.LoginResponse;

@RequestMapping("/auth")
@RestController
public class AuthController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
    
	@Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtTokenUtil.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtTokenUtil.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
    
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
    	
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }
    
}