package br.com.plgs.AppClientes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
	
	private String username;
	
	private String password;
	
	private String name;
	
	private String roles;

}
