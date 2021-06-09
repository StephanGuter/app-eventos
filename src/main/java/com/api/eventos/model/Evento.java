package com.api.eventos.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tb_evento")
public class Evento extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	// PROPRIEDADES
	
	@NotBlank
	@Column(name = "nm_evento", length = 60)
	private String nome;
	
	@NotBlank
	@Column(name = "end_evento", length = 120)
	private String endereco;
	
	@NotEmpty
	@Column(name = "ds_evento", length = 255)
	private String descricao;
	
	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "dt_evento")
	private Calendar data;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_evento_convidado", 
	           joinColumns = @JoinColumn(name = "fk_evento_id"), 
	           inverseJoinColumns = @JoinColumn(name = "fk_convidado_id"))
	private List<Convidado> convidados;

	// CONSTRUTORES
	
	public Evento() { }
	
	// GETTERS E SETTERS
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public List<Convidado> getConvidados() {
		return convidados;
	}
	
	@JsonProperty
	public void setConvidados(List<Convidado> convidados) {
		this.convidados = convidados;
	}
}