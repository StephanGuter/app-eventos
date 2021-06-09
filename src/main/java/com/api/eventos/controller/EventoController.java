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

import com.api.eventos.model.Evento;
import com.api.eventos.service.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/evento")
public class EventoController implements ControllerInterface<Evento>{

	@Autowired
	private EventoService service;
	
	// GET EVENTO FIND ALL
	
	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Retorna uma lista com todos os eventos"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Retorna uma lista com todos os eventos")
	@GetMapping
	public ResponseEntity<List<Evento>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	// GET EVENTO COM ID
	
	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Retorna um evento, dado seu id"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "404",
			description = "Não existe nenhum evento com este id"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Retorna um evento, dado seu id")
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		Evento _evento = service.findById(id);
		if (_evento != null)
			return ResponseEntity.ok(_evento);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// POST EVENTO

	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Grava um evento"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Grava um evento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(produces = "application/json")
	public ResponseEntity<Evento> post(@Valid @RequestBody Evento obj) {
		service.create(obj);
		return ResponseEntity.ok(obj);
	}

	// PUT EVENTO

	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Atualiza um evento, dado seu id"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "404",
			description = "Não existe nenhum convidado com este id"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Atualiza um evento, dado seu id")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> put(@Valid @RequestBody Evento obj) {
		if (service.update(obj)) {
			return ResponseEntity.ok(obj);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// DELETE EVENTO
	
	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Deleta um evento, dado seu id"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "404",
			description = "Não existe nenhum evento com este id"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Deleta um evento, dado seu id")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}