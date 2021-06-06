package com.api.eventos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.eventos.exception.AuthorizationException;
import com.api.eventos.model.Convidado;
import com.api.eventos.repository.ConvidadoRepository;
import com.api.eventos.security.JWTUtil;
import com.api.eventos.security.UserDetailsImpl;

@Service
public class ConvidadoService implements ServiceInterface<Convidado>{
	
	@Autowired
	private ConvidadoRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public static UserDetailsImpl authenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return (UserDetailsImpl) auth.getPrincipal();
		}
		return null;
	}

	@Override
	public Convidado create(Convidado obj) {
		obj.setSenha(passwordEncoder.encode(obj.getSenha()));
		repository.save(obj);
		return obj;
	}

	@Override
	public Convidado findById(Long id) {
		if (!jwtUtil.authorized(id)) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<Convidado> _convidado = repository.findById(id);
		return _convidado.orElse(null);
	}

	@Override
	public List<Convidado> findAll() {
		return repository.findAll();
	}

	@Override
	public boolean update(Convidado obj) {
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