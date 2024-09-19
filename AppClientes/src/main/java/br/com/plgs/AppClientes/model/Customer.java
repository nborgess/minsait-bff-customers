package br.com.plgs.AppClientes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="tb_cliente")
public class Customer {
	
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
  
}

