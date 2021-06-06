package com.api.eventos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.eventos.model.Convidado;
import com.api.eventos.repository.ConvidadoRepository;
import com.api.eventos.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ConvidadoRepository repo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Convidado _convidado = repo.findByLogin(username);
		if (_convidado == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(_convidado.getId(), _convidado.getLogin(), 
				_convidado.getSenha(), _convidado.getPerfis());
	}
}