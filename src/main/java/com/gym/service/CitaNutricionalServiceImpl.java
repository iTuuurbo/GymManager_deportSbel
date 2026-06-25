package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.CitaNutricional;
import com.gym.entity.Cliente;
import com.gym.entity.EstadoCita;
import com.gym.entity.EstadoMembresia;
import com.gym.entity.EstadoRegistro;
import com.gym.entity.Membresia;
import com.gym.entity.Nutricionista;
import com.gym.repository.CitaNutricionalRepository;
import com.gym.repository.ClienteRepository;
import com.gym.repository.NutricionistaRepository;

@Service
public class CitaNutricionalServiceImpl implements CitaNutricionalService {

	@Autowired
	private CitaNutricionalRepository citaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private NutricionistaRepository nutricionistaRepository;

	@Autowired
	private MembresiaService membresiaService;

	@Override
	public List<CitaNutricional> listar() {
		return citaRepository.findAll();
	}

	@Override
	public CitaNutricional buscarPorId(Integer id) {
		return citaRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(CitaNutricional cita) {
		if (cita.getEstado() == null) {
			cita.setEstado(EstadoCita.PENDIENTE);
		}
		Cliente cliente = resolverCliente(cita.getCliente());

		if (cliente.getEstado() != EstadoRegistro.ACTIVO) {
			throw new IllegalArgumentException("El cliente esta " + cliente.getEstado() + ". Debe estar ACTIVO para agendar citas.");
		}

		boolean tieneActiva = false;
		for (Membresia m : membresiaService.listarPorCliente(cliente.getIdCliente())) {
			if (m.getEstado() == EstadoMembresia.ACTIVA) {
				tieneActiva = true;
				break;
			}
		}
		if (!tieneActiva) {
			throw new IllegalArgumentException("El cliente no tiene una membresia ACTIVA. No puede agendar citas.");
		}

		cita.setCliente(cliente);
		Nutricionista nutri = resolverNutricionista(cita.getNutricionista());
		cita.setNutricionista(nutri);

		if (cita.getFecha() != null && cita.getFecha().isBefore(java.time.LocalDate.now())) {
			throw new IllegalArgumentException("No se puede agendar una cita en una fecha pasada.");
		}
		for (CitaNutricional c : citaRepository.findByNutricionista_IdNutricionistaAndFecha(nutri.getIdNutricionista(), cita.getFecha())) {
			if (c.getEstado() != EstadoCita.CANCELADA && c.getHora() != null && c.getHora().equals(cita.getHora())) {
				throw new IllegalArgumentException("El nutricionista ya tiene una cita a esa hora.");
			}
		}

		citaRepository.save(cita);
	}

	@Override
	public void actualizar(CitaNutricional cita) {
		CitaNutricional existente = citaRepository.findById(cita.getIdCita())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe la cita con id: " + cita.getIdCita()));
		existente.setFecha(cita.getFecha());
		existente.setHora(cita.getHora());
		existente.setEstado(cita.getEstado());
		existente.setObservaciones(cita.getObservaciones());
		existente.setCliente(resolverCliente(cita.getCliente()));
		existente.setNutricionista(resolverNutricionista(cita.getNutricionista()));
		citaRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		CitaNutricional cita = citaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No existe la cita con id: " + id));
		cita.setEstado(EstadoCita.CANCELADA);
		citaRepository.save(cita);
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepository.findClientesActivosConMembresiaActiva();
	}

	@Override
	public List<Nutricionista> listarNutricionistas() {
		return nutricionistaRepository.findByEstado((byte) 1);
	}

	private Cliente resolverCliente(Cliente seleccionado) {
		int id = (seleccionado != null) ? seleccionado.getIdCliente() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar un cliente.");
		}
		return clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El cliente no existe (id " + id + ")."));
	}

	private Nutricionista resolverNutricionista(Nutricionista seleccionado) {
		int id = (seleccionado != null) ? seleccionado.getIdNutricionista() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar un nutricionista.");
		}
		Nutricionista nutricionista = nutricionistaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El nutricionista no existe (id " + id + ")."));
		if (nutricionista.getEstado() != 1) {
			throw new IllegalArgumentException("El nutricionista esta inactivo. Debe estar activo.");
		}
		return nutricionista;
	}
}
