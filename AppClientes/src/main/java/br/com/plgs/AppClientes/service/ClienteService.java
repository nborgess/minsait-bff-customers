package br.com.plgs.AppClientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.plgs.AppClientes.exception.ClienteException;
import br.com.plgs.AppClientes.model.Cliente;
import br.com.plgs.AppClientes.repository.ClienteRepository;
import br.com.plgs.AppClientes.service.interfaces.ClienteServiceInterface;

@Service
public class ClienteService implements ClienteServiceInterface {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public Cliente save(Cliente cliente) {
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new ClienteException("O e-mail fornecido já está em uso.");
        }

        return clienteRepository.save(cliente);
	}

}
