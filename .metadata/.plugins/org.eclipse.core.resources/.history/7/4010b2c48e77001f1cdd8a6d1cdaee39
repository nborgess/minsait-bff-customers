package br.com.plgs.AppClientes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
	
	@NotBlank(message = "O Username não pode ser nulo ou vazio.")
	private String username;
	
	@NotBlank(message = "A senha não pode ser nula ou vazia.")
	private String password;
	
	@NotBlank(message = "O nome não pode ser nulo ou vazio.")
	private String name;
	
	private String roles;

}
