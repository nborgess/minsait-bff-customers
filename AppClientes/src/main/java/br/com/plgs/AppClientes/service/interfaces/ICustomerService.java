package br.com.plgs.AppClientes.service.interfaces;

import java.util.List;
import java.util.Optional;

import br.com.plgs.AppClientes.utils.CustomerRequest;
import br.com.plgs.AppClientes.utils.CustomerResponse;

public interface ICustomerService {

	CustomerResponse save(CustomerRequest requestCustomer);

	Optional<CustomerResponse> findById(Long id);

	List<CustomerResponse> findAll();

	CustomerResponse update(CustomerRequest requestCustomer, Long id);

	void delete(Long id);
}