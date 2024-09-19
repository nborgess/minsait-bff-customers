package br.com.plgs.AppClientes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.plgs.AppClientes.utils.RequestCustomer;
import br.com.plgs.AppClientes.utils.ResponseCustomer;
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

	public ResponseCustomer save(RequestCustomer requestCustomer) {
		// Verifica se o email já está em uso
		if (customerRepository.findByEmail(requestCustomer.getEmail()).isPresent()) {
			throw new CustomerException("O e-mail fornecido já está em uso.");
		}

		// Busca o endereço pelo CEP
		Address address = cepService.searchCep(requestCustomer.getZipCode());

		if (address == null || address.getAddress() == null || address.getCity() == null) {
			throw new CustomerException("CEP inválido ou não encontrado.");
		}

		// Cria um novo Customer a partir de RequestCustomer
		Customer customer = new Customer();
		customer.setName(requestCustomer.getName());
		customer.setEmail(requestCustomer.getEmail());
		customer.setCellPhone(requestCustomer.getCellPhone());
		customer.setNumberHouse(requestCustomer.getNumberHouse());
		customer.setZipCode(requestCustomer.getZipCode());

		// Associa o endereço ao cliente
		address.setCustomer(customer);
		customer.setAddress(address);

		// Salva o cliente no repositório
		Customer savedCustomer = customerRepository.save(customer);

		// Converte o Customer salvo para ResponseCustomer
		ResponseCustomer responseCustomer = new ResponseCustomer();
		responseCustomer.setId(savedCustomer.getId());
		responseCustomer.setName(savedCustomer.getName());
		responseCustomer.setEmail(savedCustomer.getEmail());
		responseCustomer.setCellPhone(savedCustomer.getCellPhone());
		responseCustomer.setNumberHouse(savedCustomer.getNumberHouse());
		responseCustomer.setZipCode(savedCustomer.getZipCode());
		responseCustomer.setAddress(savedCustomer.getAddress()); // Se necessário

		return responseCustomer;
	}

	public Optional<ResponseCustomer> findById(Long id) {
		Customer findCustomer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Não há cliente com o ID fornecido."));

		// Converte Customer em ResponseCustomer
		ResponseCustomer responseCustomer = new ResponseCustomer();
		responseCustomer.setId(findCustomer.getId());
		responseCustomer.setName(findCustomer.getName());
		responseCustomer.setEmail(findCustomer.getEmail());
		responseCustomer.setCellPhone(findCustomer.getCellPhone());
		responseCustomer.setNumberHouse(findCustomer.getNumberHouse());
		responseCustomer.setZipCode(findCustomer.getZipCode());
		responseCustomer.setAddress(findCustomer.getAddress()); // Se necessário

		return Optional.of(responseCustomer);
	}


	public List<ResponseCustomer> findAll() {
		List<Customer> customers = customerRepository.findAll();

		// Converte a lista de Customer para ResponseCustomer
		List<ResponseCustomer> responseCustomers = customers.stream()
				.map(customer -> {
					ResponseCustomer responseCustomer = new ResponseCustomer();
					responseCustomer.setId(customer.getId());
					responseCustomer.setName(customer.getName());
					responseCustomer.setEmail(customer.getEmail());
					responseCustomer.setCellPhone(customer.getCellPhone());
					responseCustomer.setNumberHouse(customer.getNumberHouse());
					responseCustomer.setZipCode(customer.getZipCode());
					responseCustomer.setAddress(customer.getAddress()); // Se necessário
					return responseCustomer;
				})
				.collect(Collectors.toList());

		return responseCustomers;
	}


	public ResponseCustomer update(RequestCustomer requestCustomer, Long id) {
		Optional<Customer> findCustomer = customerRepository.findById(id);

		if (!findCustomer.isPresent()) {
			throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
		}

		Customer updCustomer = findCustomer.get();

		// Verifica se o email foi alterado e se já está em uso
		if (!updCustomer.getEmail().equals(requestCustomer.getEmail()) &&
				customerRepository.findByEmail(requestCustomer.getEmail()).isPresent()) {
			throw new CustomerException("O e-mail fornecido já está em uso.");
		}

		// Atualiza os dados do cliente
		updCustomer.setName(requestCustomer.getName());
		updCustomer.setEmail(requestCustomer.getEmail());
		updCustomer.setCellPhone(requestCustomer.getCellPhone());
		updCustomer.setNumberHouse(requestCustomer.getNumberHouse());
		updCustomer.setZipCode(requestCustomer.getZipCode());

		// Busca o novo endereço pelo CEP
		Address newAddress = cepService.searchCep(requestCustomer.getZipCode());

		if (newAddress == null || newAddress.getAddress() == null) {
			throw new CustomerException("CEP inválido ou não encontrado.");
		}

		Address existingAddress = updCustomer.getAddress();

		if (existingAddress != null) {
			// Atualiza os dados do endereço existente
			existingAddress.setZipCode(newAddress.getZipCode());
			existingAddress.setAddress(newAddress.getAddress());
			existingAddress.setComplement(newAddress.getComplement());
			existingAddress.setDistrict(newAddress.getDistrict());
			existingAddress.setState(newAddress.getState());
			existingAddress.setCity(newAddress.getCity());
		} else {
			// Se não existir um endereço, associa o novo endereço
			updCustomer.setAddress(newAddress);
		}

		// Salva as alterações no repositório
		Customer savedCustomer = customerRepository.save(updCustomer);

		// Converte o Customer salvo para ResponseCustomer
		ResponseCustomer responseCustomer = new ResponseCustomer();
		responseCustomer.setId(savedCustomer.getId());
		responseCustomer.setName(savedCustomer.getName());
		responseCustomer.setEmail(savedCustomer.getEmail());
		responseCustomer.setCellPhone(savedCustomer.getCellPhone());
		responseCustomer.setNumberHouse(savedCustomer.getNumberHouse());
		responseCustomer.setZipCode(savedCustomer.getZipCode());
		responseCustomer.setAddress(savedCustomer.getAddress()); // Se necessário

		return responseCustomer;
	}

<<<<<<< HEAD
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
=======
>>>>>>> 6b6e4452115bc72d540c48213f64e2a090fba734

	public void delete(Long id) {
		Optional<Customer> findCustomer = customerRepository.findById(id);
		
	    if (!findCustomer.isPresent()) {
	        throw new CustomerNotFoundException("Não há cliente com o ID fornecido.");
	    }		
	    
		customerRepository.deleteById(id);
		
	}

}