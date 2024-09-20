package br.com.plgs.AppClientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.plgs.AppClientes.dto.LoginUserDto;
import br.com.plgs.AppClientes.dto.RegisterUserDto;
import br.com.plgs.AppClientes.exception.InvalidCredentialsException;
import br.com.plgs.AppClientes.exception.TokenExpiredException;
import br.com.plgs.AppClientes.model.Roles;
import br.com.plgs.AppClientes.model.User;
import br.com.plgs.AppClientes.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
    	
        if (userRepository.findByUsername(input.getUsername()).isPresent()) {
            throw new IllegalArgumentException("O nome de usuário já está em uso.");
        }
    	
        String roles = input.getRoles().toUpperCase();
        
        if (!roles.equals("ADMIN") && !roles.equals("USER")) {
            throw new IllegalArgumentException("Deve ser 'ADMIN' ou 'USER'.");
        }
    	
        User user = new User()
                .setUsername(input.getUsername())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setName(input.getName())
                .setRoles(Roles.valueOf(roles));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Nome de usuário ou senha incorretos.");
        }

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Nome de usuário ou senha incorretos."));
    }
    
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new TokenExpiredException("Token de autenticação inválido ou expirado.");
        }
        
        return (User) authentication.getPrincipal();
    }
}