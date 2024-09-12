package br.com.plgs.AppClientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.plgs.AppClientes.exception.CustomerException;
import br.com.plgs.AppClientes.exception.CustomerNotFoundException;
import br.com.plgs.AppClientes.model.Customer;
import br.com.plgs.AppClientes.repository.CustomerRepository;
import br.com.plgs.AppClientes.service.interfaces.CustomerServiceInterface;

@Service
public class CustomerService implements CustomerServiceInterface {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer save(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new CustomerException("O e-mail fornecido já está em uso.");
        }

        return customerRepository.save(customer);
	}
	
	public Optional<Customer> findById(Long id) {
		Optional<Customer> findCustomer = customerRepository.findById(id);
		
		if(!findCustomer.isPresent()) {
			throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
		}
		
		return customerRepository.findById(id);
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer update(Customer customer, Long id) {
		Optional<Customer> findCustomer = customerRepository.findById(id);
		
		if(!findCustomer.isPresent()) {
			throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
		}
		
        Customer updCustomer = findCustomer.get();
        if (!updCustomer.getEmail().equals(customer.getEmail()) &&
                customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new CustomerException("O e-mail fornecido já está em uso.");
        }

        updCustomer.setNome(customer.getNome());
        updCustomer.setEmail(customer.getEmail());
        updCustomer.setTelefone(customer.getTelefone());
        updCustomer.setEndereco(customer.getEndereco());

        return customerRepository.save(updCustomer);
	}

	public void delete(Long id) {
		Optional<Customer> findCustomer = customerRepository.findById(id);
		
	    if (!findCustomer.isPresent()) {
	        throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
	    }		
	    
		customerRepository.deleteById(id);
		
	}

}
