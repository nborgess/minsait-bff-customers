package br.com.plgs.AppClientes.controller;


import br.com.plgs.AppClientes.model.Customer;
import br.com.plgs.AppClientes.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(CustomerController.class)
public class CustomerController {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private ObjectMapper objectMapper;

    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSaveCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setNome("John Doe");

        given(customerService.save(any(Customer.class))).willReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/clientes/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testFindById() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setNome("John Doe");

        given(customerService.findById(anyLong())).willReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }
    @Test
    void testFindAllCustomers() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setNome("John Doe");

        given(customerService.findAll()).willReturn(Collections.singletonList(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setNome("John Doe");

        given(customerService.update(any(Customer.class), anyLong())).willReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        willDoNothing().given(customerService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clientes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Cliente removido com sucesso."));
    }
}
