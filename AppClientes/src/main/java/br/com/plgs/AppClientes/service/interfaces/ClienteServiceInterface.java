package br.com.plgs.AppClientes.service.interfaces;

import java.util.List;

import br.com.plgs.AppClientes.model.Cliente;

public interface ClienteServiceInterface {
	
	Cliente save(Cliente cliente);
	List<Cliente> findAll();
	Cliente update(Cliente cliente, Long id);
	void delete(Long id);

}
