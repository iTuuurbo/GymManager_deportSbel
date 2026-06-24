package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.EstadoRegistro;
import com.gym.entity.Instructor;
import com.gym.entity.Usuario;
import com.gym.repository.InstructorRepository;
import com.gym.repository.UsuarioRepository;

@Service
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorRepository instructorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Instructor> listar() {
		return instructorRepository.findAll();
	}

	@Override
	public Instructor buscarPorId(Integer id) {
		return instructorRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(Instructor instructor) {
		if (instructor.getEstado() == null) {
			instructor.setEstado(EstadoRegistro.ACTIVO);
		}
		instructor.setUsuario(resolverUsuario(instructor.getUsuario()));
		instructorRepository.save(instructor);
	}

	@Override
	public void actualizar(Instructor instructor) {
		Instructor existente = instructorRepository.findById(instructor.getIdInstructor())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el instructor con id: " + instructor.getIdInstructor()));
		existente.setDni(instructor.getDni());
		existente.setNombres(instructor.getNombres());
		existente.setApellidos(instructor.getApellidos());
		existente.setEspecialidad(instructor.getEspecialidad());
		existente.setTelefono(instructor.getTelefono());
		existente.setEstado(instructor.getEstado());
		existente.setUsuario(resolverUsuario(instructor.getUsuario()));
		existente.setDirectorioFotoPerfil(instructor.getDirectorioFotoPerfil());
		instructorRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		Instructor instructor = instructorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el instructor con id: " + id));
		instructor.setEstado(EstadoRegistro.INACTIVO);
		instructorRepository.save(instructor);
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
