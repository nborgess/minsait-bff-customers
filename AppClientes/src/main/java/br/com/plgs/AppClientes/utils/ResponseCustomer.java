package br.com.plgs.AppClientes.utils;

import br.com.plgs.AppClientes.model.Address;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "tb_cliente")
public class ResponseCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "O email não pode ser nulo ou vazio.")
    @Email(message = "E-mail inválido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "O telefone não pode ser nulo ou vazio.")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "Telefone deve seguir o formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
    @Column(nullable = false)
    private String cellPhone;

    @NotNull(message = "O número da residência não pode ser nulo.")
    @Column(nullable = false)
    private Integer numberHouse;

    @NotBlank(message = "O cep não pode ser nulo ou vazio.")
    @Column(nullable = false)
    private String zipCode;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Address address;

}
