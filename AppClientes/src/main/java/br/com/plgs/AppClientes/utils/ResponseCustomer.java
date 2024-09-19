package br.com.plgs.AppClientes.utils;

import br.com.plgs.AppClientes.model.Address;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


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
    @Pattern(regexp = "^\\+\\d{1,3} \\d{1,3} \\d{4,5}-\\d{4}$", message = "Telefone deve seguir o formato internacional.")
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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Integer getNumberHouse() {
        return numberHouse;
    }

    public void setNumberHouse(Integer numberHouse) {
        this.numberHouse = numberHouse;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
