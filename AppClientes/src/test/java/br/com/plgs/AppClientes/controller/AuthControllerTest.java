package br.com.plgs.AppClientes.controller;

import br.com.plgs.AppClientes.configuration.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class AuthControllerTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthController authController;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateToken() {
        String username = "teste";
        Map<String, Object> tokenResponse = Map.of("token", "testejwt");
        when(jwtTokenUtil.createToken(username)).thenReturn(tokenResponse);

        ResponseEntity<?> responseEntity = authController.createToken(username);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(tokenResponse, responseEntity.getBody());
    }
}
