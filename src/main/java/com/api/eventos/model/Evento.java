package com.api.eventos.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="tb_evento")
public class Evento extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	// PROPRIEDADES
	
	@NotEmpty
	@Column(name = "nm_evento", length = 60)
	private String nome;
	
	@NotEmpty
	@Column(name = "end_evento", length = 120)
	private String endereco;
	
	@NotEmpty
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "dt_evento")
	@Past
	private Calendar data;
	
	@ManyToMany
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

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public List<Convidado> getConvidados() {
		return convidados;
	}

	public void setConvidados(List<Convidado> convidados) {
		this.convidados = convidados;
	}
}