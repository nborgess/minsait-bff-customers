package br.com.plgs.AppClientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.plgs.AppClientes.exception.CustomerException;
import br.com.plgs.AppClientes.exception.CustomerNotFoundException;
import br.com.plgs.AppClientes.model.Address;
import br.com.plgs.AppClientes.model.Customer;
import br.com.plgs.AppClientes.repository.CustomerRepository;
import br.com.plgs.AppClientes.service.interfaces.CustomerServiceInterface;

@Service
public class CustomerService implements CustomerServiceInterface {

	@Autowired
	private CustomerRepository customerRepository;
	
    @Autowired
    private CepService cepService;
	
	public Customer save(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new CustomerException("O e-mail fornecido já está em uso.");
        }
        
        Address address = cepService.searchCep(customer.getZipCode());
        
        if (address == null || address.getAddress() == null || address.getCity() == null) {
            throw new CustomerException("CEP inválido ou não encontrado.");
        }
        
        address.setCustomer(customer);
        customer.setAddress(address);

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

	    if (!findCustomer.isPresent()) {
	        throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
	    }

	    Customer updCustomer = findCustomer.get();
	    if (!updCustomer.getEmail().equals(customer.getEmail()) &&
	            customerRepository.findByEmail(customer.getEmail()).isPresent()) {
	        throw new CustomerException("O e-mail fornecido já está em uso.");
	    }

	    updCustomer.setName(customer.getName());
	    updCustomer.setEmail(customer.getEmail());
	    updCustomer.setCellPhone(customer.getCellPhone());
	    updCustomer.setNumberHouse(customer.getNumberHouse());
	    updCustomer.setZipCode(customer.getZipCode());

	    Address newAddress = cepService.searchCep(customer.getZipCode());

	    if (newAddress == null || newAddress.getAddress() == null) {
	        throw new CustomerException("CEP inválido ou não encontrado.");
	    }

	    Address existingAddress = updCustomer.getAddress();

	    if (existingAddress != null) {
	        existingAddress.setZipCode(newAddress.getZipCode());
	        existingAddress.setAddress(newAddress.getAddress());
	        existingAddress.setComplement(newAddress.getComplement());
	        existingAddress.setDistrict(newAddress.getDistrict());
	        existingAddress.setState(newAddress.getState());
	        existingAddress.setCity(newAddress.getCity());
	    } else {
	        updCustomer.setAddress(newAddress);
	    }

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