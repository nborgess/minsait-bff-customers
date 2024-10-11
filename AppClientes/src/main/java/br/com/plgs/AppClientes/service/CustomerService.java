package br.com.plgs.AppClientes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.plgs.AppClientes.utils.CustomerRequest;
import br.com.plgs.AppClientes.utils.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.plgs.AppClientes.exception.CustomerException;
import br.com.plgs.AppClientes.exception.CustomerNotFoundException;
import br.com.plgs.AppClientes.model.Address;
import br.com.plgs.AppClientes.model.Customer;
import br.com.plgs.AppClientes.repository.CustomerRepository;
import br.com.plgs.AppClientes.service.interfaces.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
    @Autowired
    private CepService cepService;

	public CustomerResponse save(CustomerRequest requestCustomer) {

		if (customerRepository.findByEmail(requestCustomer.getEmail()).isPresent()) {
			throw new CustomerException("O e-mail fornecido já está em uso.");
		}

		Address address = cepService.searchCep(requestCustomer.getZipCode());

		if (address == null || address.getAddress() == null || address.getCity() == null) {
			throw new CustomerException("CEP inválido ou não encontrado.");
		}

		Customer customer = new Customer();
		customer.setName(requestCustomer.getName());
		customer.setEmail(requestCustomer.getEmail());
		customer.setCellPhone(requestCustomer.getCellPhone());
		customer.setNumberHouse(requestCustomer.getNumberHouse());
		customer.setZipCode(requestCustomer.getZipCode());

		address.setCustomer(customer);
		customer.setAddress(address);

		Customer savedCustomer = customerRepository.save(customer);

		CustomerResponse responseCustomer = new CustomerResponse();
		responseCustomer.setId(savedCustomer.getId());
		responseCustomer.setName(savedCustomer.getName());
		responseCustomer.setEmail(savedCustomer.getEmail());
		responseCustomer.setCellPhone(savedCustomer.getCellPhone());
		responseCustomer.setNumberHouse(savedCustomer.getNumberHouse());
		responseCustomer.setZipCode(savedCustomer.getZipCode());
		responseCustomer.setAddress(savedCustomer.getAddress()); 

		return responseCustomer;
	}

	public Optional<CustomerResponse> findById(Long id) {
		Customer findCustomer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Não há cliente com o ID fornecido."));

		CustomerResponse responseCustomer = new CustomerResponse();
		responseCustomer.setId(findCustomer.getId());
		responseCustomer.setName(findCustomer.getName());
		responseCustomer.setEmail(findCustomer.getEmail());
		responseCustomer.setCellPhone(findCustomer.getCellPhone());
		responseCustomer.setNumberHouse(findCustomer.getNumberHouse());
		responseCustomer.setZipCode(findCustomer.getZipCode());
		responseCustomer.setAddress(findCustomer.getAddress()); 

		return Optional.of(responseCustomer);
	}


	public List<CustomerResponse> findAll() {
		List<Customer> customers = customerRepository.findAll();

		List<CustomerResponse> responseCustomers = customers.stream()
				.map(customer -> {
					CustomerResponse responseCustomer = new CustomerResponse();
					responseCustomer.setId(customer.getId());
					responseCustomer.setName(customer.getName());
					responseCustomer.setEmail(customer.getEmail());
					responseCustomer.setCellPhone(customer.getCellPhone());
					responseCustomer.setNumberHouse(customer.getNumberHouse());
					responseCustomer.setZipCode(customer.getZipCode());
					responseCustomer.setAddress(customer.getAddress()); 
					return responseCustomer;
				})
				.collect(Collectors.toList());

		return responseCustomers;
	}


	public CustomerResponse update(CustomerRequest requestCustomer, Long id) {
		Optional<Customer> findCustomer = customerRepository.findById(id);

		if (!findCustomer.isPresent()) {
			throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
		}

		Customer updCustomer = findCustomer.get();

		if (!updCustomer.getEmail().equals(requestCustomer.getEmail()) &&
				customerRepository.findByEmail(requestCustomer.getEmail()).isPresent()) {
			throw new CustomerException("O e-mail fornecido já está em uso.");
		}

		updCustomer.setName(requestCustomer.getName());
		updCustomer.setEmail(requestCustomer.getEmail());
		updCustomer.setCellPhone(requestCustomer.getCellPhone());
		updCustomer.setNumberHouse(requestCustomer.getNumberHouse());
		updCustomer.setZipCode(requestCustomer.getZipCode());

		Address newAddress = cepService.searchCep(requestCustomer.getZipCode());

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

		Customer savedCustomer = customerRepository.save(updCustomer);

		CustomerResponse responseCustomer = new CustomerResponse();
		responseCustomer.setId(savedCustomer.getId());
		responseCustomer.setName(savedCustomer.getName());
		responseCustomer.setEmail(savedCustomer.getEmail());
		responseCustomer.setCellPhone(savedCustomer.getCellPhone());
		responseCustomer.setNumberHouse(savedCustomer.getNumberHouse());
		responseCustomer.setZipCode(savedCustomer.getZipCode());
		responseCustomer.setAddress(savedCustomer.getAddress());

		return responseCustomer;
	}


	public void delete(Long id) {
		Optional<Customer> findCustomer = customerRepository.findById(id);
		
	    if (!findCustomer.isPresent()) {
	        throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
	    }		
	    
		customerRepository.deleteById(id);
		
	}

}