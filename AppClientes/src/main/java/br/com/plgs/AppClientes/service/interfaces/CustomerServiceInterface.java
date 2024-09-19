package br.com.plgs.AppClientes.service.interfaces;

import java.util.List;
import java.util.Optional;

import br.com.plgs.AppClientes.model.Customer;
import br.com.plgs.AppClientes.utils.RequestCustomer;
import br.com.plgs.AppClientes.utils.ResponseCustomer;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceInterface {

	ResponseCustomer save(RequestCustomer requestCustomer);

	Optional<ResponseCustomer> findById(Long id);

	List<ResponseCustomer> findAll();

	ResponseCustomer update(RequestCustomer requestCustomer, Long id);

	void delete(Long id);
}

