package com.api.eventos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.eventos.model.Evento;
import com.api.eventos.repository.EventoRepository;

@Service
public class EventoService implements ServiceInterface<Evento> {

	@Autowired
	private EventoRepository repository;
	
	@Override
	public Evento create(Evento obj) {
		repository.save(obj);
		return obj;
	}

	@Override
	public Evento findById(Long id) {
		Optional<Evento> _evento = repository.findById(id);
		return _evento.orElse(null);
	}

	@Override
	public List<Evento> findAll() {
		return repository.findAll();
	}

	@Override
	public boolean update(Evento obj) {
		if (repository.existsById(obj.getId())) {
			repository.save(obj);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}
}