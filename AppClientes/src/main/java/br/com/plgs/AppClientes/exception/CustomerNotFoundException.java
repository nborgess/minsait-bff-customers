package br.com.plgs.AppClientes.exception;

public class CustomerNotFoundException extends RuntimeException {
	
    public CustomerNotFoundException(String message) {
        super(message);
    }

}
