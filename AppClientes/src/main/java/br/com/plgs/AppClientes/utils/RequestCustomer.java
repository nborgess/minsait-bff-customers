package br.com.plgs.AppClientes.utils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class RequestCustomer {

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    private String name;

    @NotBlank(message = "O email não pode ser nulo ou vazio.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "O telefone não pode ser nulo ou vazio.")
    @Pattern(regexp = "^\\+\\d{1,3} \\d{1,3} \\d{4,5}-\\d{4}$", message = "Telefone deve seguir o formato internacional.")
    private String cellPhone;

    @NotNull(message = "O número da residência não pode ser nulo.")
    private Integer numberHouse;

    @NotBlank(message = "O cep não pode ser nulo ou vazio.")
    private String zipCode;

    // Getters and Setters
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
}
