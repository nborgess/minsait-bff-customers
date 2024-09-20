package br.com.plgs.AppClientes.exception;

public class TokenExpiredException extends RuntimeException {
	
    public TokenExpiredException(String message) {
        super(message);
    }

}
