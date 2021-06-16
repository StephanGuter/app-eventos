package com.api.eventos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.eventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
	Optional<Evento> findById(Long id);
}