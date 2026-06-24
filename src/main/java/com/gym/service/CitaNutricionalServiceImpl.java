package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.CitaNutricional;
import com.gym.entity.Cliente;
import com.gym.entity.EstadoCita;
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
		cita.setCliente(resolverCliente(cita.getCliente()));
		cita.setNutricionista(resolverNutricionista(cita.getNutricionista()));
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
		return clienteRepository.findAll();
	}

	@Override
	public List<Nutricionista> listarNutricionistas() {
		return nutricionistaRepository.findAll();
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
		return nutricionistaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El nutricionista no existe (id " + id + ")."));
	}
}
