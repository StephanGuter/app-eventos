package com.api.eventos.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tb_convidado")
public class Convidado extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	// PROPRIEDADES
	
	@NotEmpty
	@Column(name = "nm_convidado", length = 60)
	private String nome;
	
	@CPF
	@NotEmpty
	@Column(name = "cod_cpf_convidado", length = 11)
	private String cpf;

	@NotEmpty
	@Column(name = "nm_login_convidado", length = 80, unique = true)
	private String login;
	
	@NotEmpty
	@Column(name = "nm_senha_convidado")
	private String senha;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_perfil")
	private Set<Integer> perfis = new HashSet<>();
	
	// CONSTRUTORES
	
	public Convidado() { }

	// GETTERS E SETTERS
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	// GETTERS E SETTERS DE AUTENTICACAO
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonIgnore
	public String getSenha() {
		return senha;
	}

	@JsonProperty
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<TipoPerfil> getPerfis() {
		return perfis.stream().map(x -> TipoPerfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(TipoPerfil perfil) {
		this.perfis.add(perfil.getCod());
	}
}