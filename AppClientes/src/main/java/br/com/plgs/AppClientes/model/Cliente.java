package br.com.plgs.AppClientes.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="tb_cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome não pode ser nulo ou vazio.")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank(message = "O email não pode ser nulo ou vazio.")
    @Email(message = "E-mail inválido.")
    @Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "O telefone não pode ser nulo ou vazio.")
    @Pattern(regexp = "^\\+\\d{1,3} \\d{1,3} \\d{4,5}-\\d{4}$", message = "Telefone deve seguir o formato internacional.")
    @Column(nullable = false)
	private String telefone;
	
    @NotBlank(message = "O endereço não pode ser nulo ou vazio.")
	@Column(nullable = false)
	private String endereco;
	
	public Cliente() { }

	public Cliente(Long id, String nome, String email, String telefone, String endereco) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
	
	public String toString() {
		return "[id = " 		+ this.id 		+ ", " +
			   "[Nome = "       + this.nome     + ", " +
			   "[Email = "      + this.email    + ", " +
			   "[Telefone = "   + this.telefone + ", " +
			   "[Endereco = "   + this.endereco + "]";
		
	}
	

}
