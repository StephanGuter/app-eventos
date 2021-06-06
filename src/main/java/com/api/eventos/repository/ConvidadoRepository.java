package com.api.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.eventos.model.Convidado;

public interface ConvidadoRepository extends JpaRepository<Convidado, Long> {
	Convidado findByLogin(String login);
}