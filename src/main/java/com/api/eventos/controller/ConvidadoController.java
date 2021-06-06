package com.api.eventos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.eventos.model.Convidado;
import com.api.eventos.service.ConvidadoService;

import com.api.eventos.exception.AuthorizationException;

@RestController
@RequestMapping("/Convidado")
public class ConvidadoController implements ControllerInterface<Convidado> {

	@Autowired
	private ConvidadoService service;
	
	@Override
	@GetMapping
	public ResponseEntity<List<Convidado>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	@GetMapping(value="/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		try {
			Convidado _convidado = service.findById(id);
			if (_convidado != null)
				return ResponseEntity.ok(_convidado);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@PostMapping
	public ResponseEntity<Convidado> post(@Valid @RequestBody Convidado obj) {
		service.create(obj);
		return ResponseEntity.ok(obj);
	}

	@Override
	@PutMapping
	public ResponseEntity<?> put(@Valid @RequestBody Convidado obj) {
		if (service.update(obj)) {
			return ResponseEntity.ok(obj);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Override
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}