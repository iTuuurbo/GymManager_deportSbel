package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Rol;
import com.gym.entity.Usuario;
import com.gym.repository.RolRepository;
import com.gym.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

	@Override
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario buscarPorId(Integer id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(Usuario usuario) {
		usuario.setIdRol(resolverRol(usuario.getIdRol()));
		usuarioRepository.save(usuario);
	}

	@Override
	public void actualizar(Usuario usuario) {
		Usuario existente = usuarioRepository.findById(usuario.getIdUsuario())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el usuario con id: " + usuario.getIdUsuario()));

		existente.setUsername(usuario.getUsername());
		existente.setEstado(usuario.getEstado());
		existente.setIdRol(resolverRol(usuario.getIdRol()));
		// El password solo se cambia si el form envia uno nuevo (al editar va vacio).
		if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
			existente.setPassword(usuario.getPassword());
		}
		usuarioRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el usuario con id: " + id));
		usuario.setEstado((byte) 0);
		usuarioRepository.save(usuario);
	}

	@Override
	public List<Rol> listarRoles() {
		return rolRepository.findAll();
	}

	/** Trae el Rol administrado desde la BD (evita el "transient instance"). */
	private Rol resolverRol(Rol seleccionado) {
		int idRol = (seleccionado != null) ? seleccionado.getIdRol() : 0;
		if (idRol == 0) {
			throw new IllegalArgumentException("Debe seleccionar un rol.");
		}
		return rolRepository.findById(idRol)
				.orElseThrow(() -> new IllegalArgumentException(
						"El rol seleccionado no existe (id " + idRol + ")."));
	}
}
