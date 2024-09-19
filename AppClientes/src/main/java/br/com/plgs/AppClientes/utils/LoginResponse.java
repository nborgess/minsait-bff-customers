package br.com.plgs.AppClientes.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
	
    private String token;

    private Long expiresIn;

    public String getToken() {
        return token;
    }
    
    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public LoginResponse setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

}
