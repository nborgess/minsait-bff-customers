package br.com.plgs.AppClientes.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import br.com.plgs.AppClientes.utils.RequestCustomer;
import br.com.plgs.AppClientes.utils.ResponseCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.plgs.AppClientes.exception.SuccessResponse;
import br.com.plgs.AppClientes.model.Customer;
import br.com.plgs.AppClientes.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Operation(summary = "Grava o registro de Cliente")
	@PostMapping
	public ResponseEntity<ResponseCustomer> save(@Valid @RequestBody RequestCustomer requestCustomer) {
		// Chama o serviço passando o RequestCustomer
		ResponseCustomer responseCustomer = customerService.save(requestCustomer);

		// Cria a URI para o novo cliente
		URI location = URI.create("/clientes/" + responseCustomer.getId());
		return ResponseEntity.created(location).body(responseCustomer);
	}


	@Operation(summary = "Busca registro pelo ID do Cliente")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<ResponseCustomer>> findById(@Valid @PathVariable Long id) {
		Optional<ResponseCustomer> responseCustomer = customerService.findById(id);
		return ResponseEntity.ok(responseCustomer);
	}


	@Operation(summary = "Busca todos os registros de Clientes")
	@GetMapping
	public ResponseEntity<List<ResponseCustomer>> findAllCustomers() {
		List<ResponseCustomer> responseCustomers = customerService.findAll();
		return ResponseEntity.ok(responseCustomers);
	}


	@Operation(summary = "Atualiza o registro do Cliente por ID")
	@PutMapping("/{id}")
	public ResponseEntity<ResponseCustomer> update(@Valid @RequestBody RequestCustomer requestCustomer, @PathVariable Long id) {
		ResponseCustomer responseCustomer = customerService.update(requestCustomer, id);
		return ResponseEntity.ok(responseCustomer);
	}


	@Operation(summary = "Deleta o registro do Cliente por ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
		customerService.delete(id);
		SuccessResponse response = new SuccessResponse("Cliente removido com sucesso.");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

}
