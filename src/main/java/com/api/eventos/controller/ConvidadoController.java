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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.api.eventos.exception.AuthorizationException;

@RestController
@RequestMapping("/convidado")
public class ConvidadoController implements ControllerInterface<Convidado> {

	@Autowired
	private ConvidadoService service;
	
	// GET CONVIDADO FIND ALL
	
	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Retorna uma lista com todos os convidados"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Retorna uma lista com todos os convidados")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Convidado>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	// GET CONVIDADO COM ID
	
	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Retorna um convidado, dado seu id"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "404",
			description = "Não existe nenhum convidado com este id"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Retorna um convidado, dado seu id")
	@GetMapping(value="/{id}", produces = "application/json")
	public ResponseEntity<Convidado> get(@PathVariable("id") Long id) {
		try {
			Convidado _convidado = service.findById(id);
			if (_convidado != null)
				return ResponseEntity.ok(_convidado);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	// POST CONVIDADO

	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Grava um convidado"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Grava um convidado")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(produces = "application/json")
	public ResponseEntity<Convidado> post(@Valid @RequestBody Convidado obj) {
		service.create(obj);
		return ResponseEntity.ok(obj);
	}
	
	// PUT CONVIDADO

	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Atualiza um convidado, dado seu id"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "404",
			description = "Não existe nenhum convidado com este id"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Atualiza um convidado, dado seu id")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Convidado> put(@Valid @RequestBody Convidado obj) {
		if (service.update(obj)) {
			return ResponseEntity.ok(obj);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// DELETE CONVIDADO
	
	@Override
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Deleta um convidado, dado seu id"),
			@ApiResponse(responseCode = "403",
			description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "404",
			description = "Não existe nenhum convidado com este id"),
			@ApiResponse(responseCode = "500",
			description = "Foi gerada uma exceção"),
			})
	@Operation(summary = "Deleta um convidado, dado seu id")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}