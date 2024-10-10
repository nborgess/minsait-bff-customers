package br.com.plgs.AppClientes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.Collections;
import java.util.Optional;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.plgs.AppClientes.configuration.JwtTokenUtil;
import br.com.plgs.AppClientes.model.Address;
import br.com.plgs.AppClientes.service.CustomerService;
import br.com.plgs.AppClientes.utils.RequestCustomer;
import br.com.plgs.AppClientes.utils.ResponseCustomer;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;
    
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

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

        RequestCustomer requestCustomer = new RequestCustomer();
        requestCustomer.setName("Teste");
        requestCustomer.setEmail("teste@example.com");
        requestCustomer.setCellPhone("(11) 95435-5435");
        requestCustomer.setNumberHouse(155);
        requestCustomer.setZipCode("07341418");

        ResponseCustomer responseCustomer = new ResponseCustomer();
        responseCustomer.setId(1L);
        responseCustomer.setName(requestCustomer.getName());
        responseCustomer.setEmail(requestCustomer.getEmail());
        responseCustomer.setCellPhone(requestCustomer.getCellPhone());
        responseCustomer.setNumberHouse(requestCustomer.getNumberHouse());
        responseCustomer.setZipCode(requestCustomer.getZipCode());
        responseCustomer.setAddress(address); 

        given(customerService.save(any(RequestCustomer.class))).willReturn(responseCustomer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken())
                        .content(objectMapper.writeValueAsString(requestCustomer)))
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
        ResponseCustomer responseCustomer = new ResponseCustomer();
        responseCustomer.setId(1L);
        responseCustomer.setName("Teste");

        given(customerService.findById(anyLong())).willReturn(Optional.of(responseCustomer));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, createJwtToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Teste"));
    }

    @Test
    void testFindAllCustomers() throws Exception {
        ResponseCustomer responseCustomer = new ResponseCustomer();
        responseCustomer.setId(1L);
        responseCustomer.setName("Teste");

        given(customerService.findAll()).willReturn(Collections.singletonList(responseCustomer));

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

        RequestCustomer requestCustomer = new RequestCustomer();
        requestCustomer.setName("Teste Novo");
        requestCustomer.setEmail("testenovo@example.com");
        requestCustomer.setCellPhone("(11) 11111-1111");
        requestCustomer.setNumberHouse(155);
        requestCustomer.setZipCode("07341418");

        ResponseCustomer responseCustomer = new ResponseCustomer();
        responseCustomer.setId(1L);
        responseCustomer.setName(requestCustomer.getName());
        responseCustomer.setEmail(requestCustomer.getEmail());
        responseCustomer.setCellPhone(requestCustomer.getCellPhone());
        responseCustomer.setNumberHouse(requestCustomer.getNumberHouse());
        responseCustomer.setZipCode(requestCustomer.getZipCode());
        responseCustomer.setAddress(address); 

        given(customerService.update(any(RequestCustomer.class), anyLong())).willReturn(responseCustomer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCustomer))
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