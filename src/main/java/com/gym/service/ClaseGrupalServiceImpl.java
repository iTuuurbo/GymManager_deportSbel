package com.gym.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.ClaseGrupal;
import com.gym.entity.EstadoGrupal;
import com.gym.entity.EstadoRegistro;
import com.gym.entity.EstadoReserva;
import com.gym.entity.Instructor;
import com.gym.entity.Reserva;
import com.gym.repository.ClaseGrupalRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.ReservaRepository;

@Service
public class ClaseGrupalServiceImpl implements ClaseGrupalService {

	@Autowired
	private ClaseGrupalRepository claseGrupalRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@Autowired
	private ReservaRepository reservaRepository;

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
		if (claseGrupal.getFecha() != null && claseGrupal.getFecha().isBefore(java.time.LocalDate.now())) {
			throw new IllegalArgumentException("La fecha de la clase no puede ser anterior a hoy.");
		}
		if (claseGrupal.getHoraInicio() != null && claseGrupal.getHoraFin() != null
				&& !claseGrupal.getHoraInicio().isBefore(claseGrupal.getHoraFin())) {
			throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin.");
		}
		claseGrupal.setInstructor(resolverInstructor(claseGrupal.getInstructor()));
		if (claseGrupal.getCuposDisponibles() <= 0) {
			claseGrupal.setCuposDisponibles(claseGrupal.getCapacidad());
		}
		validarHorarioInstructor(claseGrupal, 0);
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
		validarHorarioInstructor(claseGrupal, claseGrupal.getIdClase());
		claseGrupalRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		ClaseGrupal claseGrupal = claseGrupalRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No existe la clase con id: " + id));
		claseGrupal.setEstadoGrupal(EstadoGrupal.CANCELADA);
		claseGrupalRepository.save(claseGrupal);
		// Al cancelar la clase, se cancelan sus reservas activas
		for (Reserva r : reservaRepository.findByClaseGrupal_IdClase(id)) {
			if (r.getEstadoReserva() != EstadoReserva.CANCELADA) {
				r.setEstadoReserva(EstadoReserva.CANCELADA);
				reservaRepository.save(r);
			}
		}
	}

	@Override
	public List<Instructor> listarInstructores() {
		return instructorRepository.findByEstado(EstadoRegistro.ACTIVO);
	}

	private Instructor resolverInstructor(Instructor seleccionado) {
		int id = (seleccionado != null) ? seleccionado.getIdInstructor() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar un instructor.");
		}
		Instructor instructor = instructorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El instructor no existe (id " + id + ")."));
		if (instructor.getEstado() != EstadoRegistro.ACTIVO) {
			throw new IllegalArgumentException("El instructor esta " + instructor.getEstado() + ". Debe estar ACTIVO.");
		}
		return instructor;
	}

	private void validarHorarioInstructor(ClaseGrupal claseGrupal, int idExcluir) {
		List<ClaseGrupal> clasesDelDia = claseGrupalRepository.findByInstructor_IdInstructorAndFecha(
				claseGrupal.getInstructor().getIdInstructor(), claseGrupal.getFecha());
		for (ClaseGrupal c : clasesDelDia) {
			if (c.getIdClase() == idExcluir) continue;
			if (c.getEstadoGrupal() == EstadoGrupal.CANCELADA) continue;
			boolean conflicto = hayConflicto(claseGrupal.getHoraInicio(), claseGrupal.getHoraFin(),
					c.getHoraInicio(), c.getHoraFin());
			if (conflicto) {
				throw new IllegalArgumentException("El instructor ya tiene asignada la clase '"
						+ c.getNombre() + "' en el horario seleccionado ("
						+ c.getHoraInicio() + " - " + c.getHoraFin() + ").");
			}
		}
	}

	private boolean hayConflicto(LocalTime ini1, LocalTime fin1, LocalTime ini2, LocalTime fin2) {
		return ini1.isBefore(fin2) && ini2.isBefore(fin1);
	}
}
