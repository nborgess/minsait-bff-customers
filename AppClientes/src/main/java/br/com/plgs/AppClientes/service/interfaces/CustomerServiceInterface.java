package br.com.plgs.AppClientes.service.interfaces;

import java.util.List;
import java.util.Optional;

import br.com.plgs.AppClientes.model.Customer;

public interface CustomerServiceInterface {
	
	Customer save(Customer customer);
	Optional<Customer> findById(Long id);
	List<Customer> findAll();
	Customer update(Customer customer, Long id);
	void delete(Long id);

}
