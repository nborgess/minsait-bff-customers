package br.com.plgs.AppClientes.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.plgs.AppClientes.exception.SuccessResponse;
import br.com.plgs.AppClientes.service.CustomerService;
import br.com.plgs.AppClientes.utils.CustomerRequest;
import br.com.plgs.AppClientes.utils.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Operation(summary = "Grava o registro de Cliente")
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<CustomerResponse> save(@Valid @RequestBody CustomerRequest requestCustomer) {
		CustomerResponse responseCustomer = customerService.save(requestCustomer);
		URI location = URI.create("/clientes/" + responseCustomer.getId());
		return ResponseEntity.created(location).body(responseCustomer);
	}


	@Operation(summary = "Busca registro pelo ID do Cliente")
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Optional<CustomerResponse>> findById(@Valid @PathVariable Long id) {
		Optional<CustomerResponse> responseCustomer = customerService.findById(id);
		return ResponseEntity.ok(responseCustomer);
	}


	@Operation(summary = "Busca todos os registros de Clientes")
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
		List<CustomerResponse> responseCustomers = customerService.findAll();
		return ResponseEntity.ok(responseCustomers);
	}


	@Operation(summary = "Atualiza o registro do Cliente por ID")
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<CustomerResponse> update(@Valid @RequestBody CustomerRequest requestCustomer, @PathVariable Long id) {
		CustomerResponse responseCustomer = customerService.update(requestCustomer, id);
		return ResponseEntity.ok(responseCustomer);
	}


	@Operation(summary = "Deleta o registro do Cliente por ID")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
		customerService.delete(id);
		SuccessResponse response = new SuccessResponse("Cliente removido com sucesso.");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

}