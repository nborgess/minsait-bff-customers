package br.com.plgs.AppClientes.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "users")
@Entity
public class User implements UserDetails {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "O Username não pode ser nulo ou vazio.")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "A senha não pode ser nula ou vazia.")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "A função não pode ser nula.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roles.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
    public User setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public User setName(String name) {
        this.name = name;
        return this;
    }
    
    public User setRoles(Roles roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}