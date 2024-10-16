package br.com.plgs.AppClientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.plgs.AppClientes.exception.ClienteException;
import br.com.plgs.AppClientes.exception.ClienteNotFoundException;
import br.com.plgs.AppClientes.model.Cliente;
import br.com.plgs.AppClientes.repository.ClienteRepository;
import br.com.plgs.AppClientes.service.interfaces.ClienteServiceInterface;

@Service
public class ClienteService implements ClienteServiceInterface {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente save(Cliente cliente) {
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new ClienteException("O e-mail fornecido já está em uso.");
        }

        return clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> findById(Long id) {
		Optional<Cliente> findCliente = clienteRepository.findById(id);
		
		if(!findCliente.isPresent()) {
			throw new ClienteNotFoundException("Não há cliente com o ID fornecido.");
		}
		
		return clienteRepository.findById(id);
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente update(Cliente cliente, Long id) {
		Optional<Cliente> findCliente = clienteRepository.findById(id);
		
		if(!findCliente.isPresent()) {
			throw new ClienteNotFoundException("Não há cliente com o ID fornecido.");
		}
		
        Cliente updCliente = findCliente.get();
        if (!updCliente.getEmail().equals(cliente.getEmail()) &&
                clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new ClienteException("O e-mail fornecido já está em uso.");
        }

        updCliente.setNome(cliente.getNome());
        updCliente.setEmail(cliente.getEmail());
        updCliente.setTelefone(cliente.getTelefone());
        updCliente.setEndereco(cliente.getEndereco());

        return clienteRepository.save(updCliente);
	}

	public void delete(Long id) {
		Optional<Cliente> findCliente = clienteRepository.findById(id);
		
	    if (!findCliente.isPresent()) {
	        throw new ClienteNotFoundException("Não há cliente com o ID fornecido.");
	    }		
	    
		clienteRepository.deleteById(id);
		
	}

}
