package br.com.plgs.AppClientes.service.interfaces;

import java.util.List;
import java.util.Optional;

import br.com.plgs.AppClientes.model.Cliente;

public interface ClienteServiceInterface {
	
	Cliente save(Cliente cliente);
	Optional<Cliente> findById(Long id);
	List<Cliente> findAll();
	Cliente update(Cliente cliente, Long id);
	void delete(Long id);

}
