package com.gym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gym.entity.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.buscarPorUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario no encontrado: " + username);
		}
		if (usuario.getEstado() == 0) {
			throw new UsernameNotFoundException("Usuario inactivo: " + username);
		}
		String rol = usuario.getIdRol() != null ? usuario.getIdRol().getNombre() : "SIN_ROL";
		return User.withUsername(usuario.getUsername())
				.password(usuario.getPassword())
				.authorities(new SimpleGrantedAuthority(rol))
				.build();
	}
}
