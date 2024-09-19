package br.com.plgs.AppClientes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.plgs.AppClientes.model.Address;

@Service
public class CepService {

<<<<<<< HEAD
	@Value("${viacep.url}")
=======
    @Value("${url.cep}")
>>>>>>> 6b6e4452115bc72d540c48213f64e2a090fba734
    private String viaCepUrl;

    public Address searchCep(String cep) {
        String url = viaCepUrl + cep + "/json";
        RestTemplate restTemplate = new RestTemplate();
        try {
            Address address = restTemplate.getForObject(url, Address.class);
            if (address == null || address.getAddress() == null || address.getCity() == null) {
                throw new IllegalArgumentException("CEP inválido ou não encontrado.");
            }
            return address;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new IllegalArgumentException("CEP inválido ou não encontrado.");
            }
            throw e;
        }
    }
}