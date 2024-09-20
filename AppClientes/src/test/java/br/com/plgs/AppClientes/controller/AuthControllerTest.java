package br.com.plgs.AppClientes.controller;

import br.com.plgs.AppClientes.configuration.JwtTokenUtil;
import br.com.plgs.AppClientes.dto.LoginUserDto;
import br.com.plgs.AppClientes.dto.RegisterUserDto;
import br.com.plgs.AppClientes.model.User;
import br.com.plgs.AppClientes.service.AuthService;
import br.com.plgs.AppClientes.utils.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class AuthControllerTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignup() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUsername("teste");
        registerUserDto.setPassword("123456");

        User mockUser = new User();
        mockUser.setUsername("teste");
        when(authService.signup(registerUserDto)).thenReturn(mockUser);

        ResponseEntity<User> responseEntity = authController.register(registerUserDto);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("teste", responseEntity.getBody().getUsername());
    }

    @Test
    public void testLogin() {
    	
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername("teste");
        loginUserDto.setPassword("123456");

        User mockUser = new User();
        mockUser.setUsername("teste");
        when(authService.authenticate(loginUserDto)).thenReturn(mockUser);
        when(jwtTokenUtil.generateToken(mockUser)).thenReturn("mockedJwtToken");
        when(jwtTokenUtil.getExpirationTime()).thenReturn(3600L);

        ResponseEntity<LoginResponse> responseEntity = authController.authenticate(loginUserDto);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("mockedJwtToken", responseEntity.getBody().getToken());
        assertEquals(3600L, responseEntity.getBody().getExpiresIn());
    }
}