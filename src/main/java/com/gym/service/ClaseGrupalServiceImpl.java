package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.ClaseGrupal;
import com.gym.entity.EstadoGrupal;
import com.gym.entity.Instructor;
import com.gym.repository.ClaseGrupalRepository;
import com.gym.repository.InstructorRepository;

@Service
public class ClaseGrupalServiceImpl implements ClaseGrupalService {

	@Autowired
	private ClaseGrupalRepository claseGrupalRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@Override
	public List<ClaseGrupal> listar() {
		return claseGrupalRepository.findAll();
	}

	@Override
	public ClaseGrupal buscarPorId(Integer id) {
		return claseGrupalRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(ClaseGrupal claseGrupal) {
		if (claseGrupal.getEstadoGrupal() == null) {
			claseGrupal.setEstadoGrupal(EstadoGrupal.PROGRAMADA);
		}
		claseGrupal.setInstructor(resolverInstructor(claseGrupal.getInstructor()));
		claseGrupalRepository.save(claseGrupal);
	}

	@Override
	public void actualizar(ClaseGrupal claseGrupal) {
		ClaseGrupal existente = claseGrupalRepository.findById(claseGrupal.getIdClase())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe la clase con id: " + claseGrupal.getIdClase()));
		existente.setNombre(claseGrupal.getNombre());
		existente.setDescripcion(claseGrupal.getDescripcion());
		existente.setCapacidad(claseGrupal.getCapacidad());
		existente.setCuposDisponibles(claseGrupal.getCuposDisponibles());
		existente.setFecha(claseGrupal.getFecha());
		existente.setHoraInicio(claseGrupal.getHoraInicio());
		existente.setHoraFin(claseGrupal.getHoraFin());
		existente.setEstadoGrupal(claseGrupal.getEstadoGrupal());
		existente.setInstructor(resolverInstructor(claseGrupal.getInstructor()));
		claseGrupalRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		ClaseGrupal claseGrupal = claseGrupalRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No existe la clase con id: " + id));
		claseGrupal.setEstadoGrupal(EstadoGrupal.CANCELADA);
		claseGrupalRepository.save(claseGrupal);
	}

	@Override
	public List<Instructor> listarInstructores() {
		return instructorRepository.findAll();
	}

	private Instructor resolverInstructor(Instructor seleccionado) {
		int id = (seleccionado != null) ? seleccionado.getIdInstructor() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar un instructor.");
		}
		return instructorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El instructor no existe (id " + id + ")."));
	}
}
