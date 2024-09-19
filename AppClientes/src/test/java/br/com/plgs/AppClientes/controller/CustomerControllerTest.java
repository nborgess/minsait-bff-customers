package br.com.plgs.AppClientes.controller;

import br.com.plgs.AppClientes.model.Address;
import br.com.plgs.AppClientes.model.Customer;
import br.com.plgs.AppClientes.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import java.util.Collections;
import java.util.Optional;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController; 

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build(); 
    }

    private String createJwtToken() {
        return "Bearer " + "mocked.jwt.token";
    }

    @Test
    void testSaveCustomer() throws Exception {
        Address address = new Address();
        address.setZipCode("07341418");
        address.setAddress("Rua Teste");
        address.setComplement("Apto 101");
        address.setDistrict("Bairro Teste");
        address.setCity("Cidade Teste");
        address.setState("SP");
    	
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste");
        customer.setEmail("teste@example.com");
        customer.setCellPhone("+55 11 95435-5435");
        customer.setNumberHouse(155);
        customer.setZipCode("07341418");
        customer.setAddress(address);

        given(customerService.save(any(Customer.class))).willReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken())
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/clientes/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("07341418"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.cep").value("07341418"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.logradouro").value("Rua Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.complemento").value("Apto 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.bairro").value("Bairro Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.localidade").value("Cidade Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.uf").value("SP"));
    }

    @Test
    void testFindById() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste");

        given(customerService.findById(anyLong())).willReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Teste")); 
    }

    @Test
    void testFindAllCustomers() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste");

        given(customerService.findAll()).willReturn(Collections.singletonList(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Teste")); 
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Address address = new Address();
        address.setZipCode("07341418");
        address.setAddress("Rua Teste");
        address.setComplement("Apto 101");
        address.setDistrict("Bairro Teste");
        address.setCity("Cidade Teste");
        address.setState("SP");
    	
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste Novo");
        customer.setEmail("testenovo@example.com");
        customer.setCellPhone("+55 11 11111-1111");
        customer.setNumberHouse(155);
        customer.setZipCode("07341418");
        customer.setAddress(address);

        given(customerService.update(any(Customer.class), anyLong())).willReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Teste Novo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testenovo@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("07341418"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.cep").value("07341418"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.logradouro").value("Rua Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.complemento").value("Apto 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.bairro").value("Bairro Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.localidade").value("Cidade Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.uf").value("SP"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        willDoNothing().given(customerService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clientes/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Cliente removido com sucesso."));
    }
}