package br.com.plgs.AppClientes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "tb_endereco")
public class Address {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column
    @JsonProperty("cep")
	private String zipCode;
	
    @Column
    @JsonProperty("logradouro")
	private String address;
	
    @Column
    @JsonProperty("complemento")
	private String complement;
	
    @Column
    @JsonProperty("bairro")
	private String district;
	
    @Column
    @JsonProperty("localidade")
	private String city;
	
    @Column
    @JsonProperty("uf")
	private String state;
	
    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

}
