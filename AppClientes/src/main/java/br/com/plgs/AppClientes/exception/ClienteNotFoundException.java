package br.com.plgs.AppClientes.exception;

public class ClienteNotFoundException extends RuntimeException {
	
    public ClienteNotFoundException(String message) {
        super(message);
    }

}
