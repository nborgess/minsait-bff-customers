package br.com.plgs.AppClientes.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse {
	
	private String mensagem;
	
    public SuccessResponse(String mensagem) {
        this.mensagem = mensagem;
    }

}
