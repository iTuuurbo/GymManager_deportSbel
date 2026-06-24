package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.entity.ClaseGrupal;
import com.gym.entity.Cliente;
import com.gym.entity.EstadoGrupal;
import com.gym.entity.EstadoMembresia;
import com.gym.entity.EstadoRegistro;
import com.gym.entity.EstadoReserva;
import com.gym.entity.Membresia;
import com.gym.entity.Reserva;
import com.gym.repository.ClaseGrupalRepository;
import com.gym.repository.ClienteRepository;
import com.gym.repository.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private ClaseGrupalRepository claseGrupalRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private MembresiaService membresiaService;

	@Override
	public List<Reserva> listar() {
		return reservaRepository.findAll();
	}

	@Override
	public Reserva buscarPorId(Integer id) {
		return reservaRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void registrar(Reserva reserva) {
		Cliente cliente = resolverCliente(reserva.getCliente());
		ClaseGrupal clase = resolverClase(reserva.getClaseGrupal());

		if (cliente.getEstado() != EstadoRegistro.ACTIVO) {
			throw new IllegalArgumentException("El cliente esta " + cliente.getEstado() + ". Debe estar ACTIVO para reservar.");
		}

		if (clase.getEstadoGrupal() != EstadoGrupal.PROGRAMADA) {
			throw new IllegalArgumentException("La clase no esta PROGRAMADA (esta " + clase.getEstadoGrupal() + "). No se puede reservar.");
		}
		if (clase.getFecha().isBefore(java.time.LocalDate.now())) {
			throw new IllegalArgumentException("No se puede reservar una clase con fecha pasada.");
		}

		boolean tieneActiva = false;
		for (Membresia m : membresiaService.listarPorCliente(cliente.getIdCliente())) {
			if (m.getEstado() == EstadoMembresia.ACTIVA) {
				tieneActiva = true;
				break;
			}
		}
		if (!tieneActiva) {
			throw new IllegalArgumentException("El cliente no tiene una membresia ACTIVA. No puede reservar.");
		}

		for (Reserva r : reservaRepository.findByClaseGrupal_IdClaseAndCliente_IdCliente(clase.getIdClase(), cliente.getIdCliente())) {
			if (r.getEstadoReserva() != EstadoReserva.CANCELADA) {
				throw new IllegalArgumentException("El cliente ya tiene una reserva activa en esta clase.");
			}
		}

		if (clase.getCuposDisponibles() <= 0) {
			throw new IllegalArgumentException("La clase no tiene cupos disponibles.");
		}
		clase.setCuposDisponibles(clase.getCuposDisponibles() - 1);
		claseGrupalRepository.save(clase);

		reserva.setClaseGrupal(clase);
		reserva.setCliente(cliente);
		if (reserva.getEstadoReserva() == null) {
			reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
		}
		reservaRepository.save(reserva);
	}

	@Override
	public void actualizar(Reserva reserva) {
		// Edicion simple: solo cambia el estado (no recalcula cupos).
		Reserva existente = reservaRepository.findById(reserva.getIdReserva())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe la reserva con id: " + reserva.getIdReserva()));
		existente.setEstadoReserva(reserva.getEstadoReserva());
		reservaRepository.save(existente);
	}

	@Override
	@Transactional
	public void desactivar(Integer id) {
		Reserva reserva = reservaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No existe la reserva con id: " + id));
		// Si no estaba cancelada, devuelve el cupo a la clase.
		if (reserva.getEstadoReserva() != EstadoReserva.CANCELADA && reserva.getClaseGrupal() != null) {
			ClaseGrupal clase = reserva.getClaseGrupal();
			clase.setCuposDisponibles(clase.getCuposDisponibles() + 1);
			claseGrupalRepository.save(clase);
		}
		reserva.setEstadoReserva(EstadoReserva.CANCELADA);
		reservaRepository.save(reserva);
	}

	@Override
	public List<ClaseGrupal> listarClases() {
		return claseGrupalRepository.findAll();
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepository.findByEstado(EstadoRegistro.ACTIVO);
	}

	private ClaseGrupal resolverClase(ClaseGrupal seleccionada) {
		int id = (seleccionada != null) ? seleccionada.getIdClase() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar una clase.");
		}
		return claseGrupalRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("La clase no existe (id " + id + ")."));
	}

	private Cliente resolverCliente(Cliente seleccionado) {
		int id = (seleccionado != null) ? seleccionado.getIdCliente() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar un cliente.");
		}
		return clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El cliente no existe (id " + id + ")."));
	}
}
