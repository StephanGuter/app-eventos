package com.api.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.eventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> { }