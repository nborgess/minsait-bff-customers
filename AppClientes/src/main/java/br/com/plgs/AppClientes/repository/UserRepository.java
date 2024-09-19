package br.com.plgs.AppClientes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.plgs.AppClientes.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String Username);

}
