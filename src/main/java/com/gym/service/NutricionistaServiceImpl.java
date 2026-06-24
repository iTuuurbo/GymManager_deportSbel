package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Nutricionista;
import com.gym.entity.Usuario;
import com.gym.repository.NutricionistaRepository;
import com.gym.repository.UsuarioRepository;

@Service
public class NutricionistaServiceImpl implements NutricionistaService {

	@Autowired
	private NutricionistaRepository nutricionistaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Nutricionista> listar() {
		return nutricionistaRepository.findAll();
	}

	@Override
	public Nutricionista buscarPorId(Integer id) {
		return nutricionistaRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(Nutricionista nutricionista) {
		nutricionista.setUsuario(resolverUsuario(nutricionista.getUsuario()));
		nutricionistaRepository.save(nutricionista);
	}

	@Override
	public void actualizar(Nutricionista nutricionista) {
		Nutricionista existente = nutricionistaRepository.findById(nutricionista.getIdNutricionista())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el nutricionista con id: " + nutricionista.getIdNutricionista()));
		existente.setDni(nutricionista.getDni());
		existente.setNombres(nutricionista.getNombres());
		existente.setApellidos(nutricionista.getApellidos());
		existente.setColegiatura(nutricionista.getColegiatura());
		existente.setTelefono(nutricionista.getTelefono());
		existente.setEstado(nutricionista.getEstado());
		existente.setUsuario(resolverUsuario(nutricionista.getUsuario()));
		nutricionistaRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		Nutricionista nutricionista = nutricionistaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el nutricionista con id: " + id));
		nutricionista.setEstado((byte) 0);
		nutricionistaRepository.save(nutricionista);
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	private Usuario resolverUsuario(Usuario seleccionado) {
		int idUsuario = (seleccionado != null) ? seleccionado.getIdUsuario() : 0;
		if (idUsuario == 0) {
			throw new IllegalArgumentException("Debe seleccionar un usuario.");
		}
		return usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new IllegalArgumentException(
						"El usuario seleccionado no existe (id " + idUsuario + ")."));
	}
}
