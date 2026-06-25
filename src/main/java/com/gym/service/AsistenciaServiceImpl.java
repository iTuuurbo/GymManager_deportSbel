package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Asistencia;
import com.gym.entity.Cliente;
import com.gym.entity.EstadoMembresia;
import com.gym.entity.EstadoRegistro;
import com.gym.repository.AsistenciaRepository;
import com.gym.repository.ClienteRepository;
import com.gym.repository.MembresiaRepository;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

	@Autowired
	private AsistenciaRepository asistenciaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private MembresiaRepository membresiaRepository;

	@Override
	public List<Asistencia> listar() {
		return asistenciaRepository.findAll();
	}

	@Override
	public Asistencia buscarPorId(Integer id) {
		return asistenciaRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(Asistencia asistencia) {
		Cliente cliente = resolverCliente(asistencia.getCliente());

		if (cliente.getEstado() != EstadoRegistro.ACTIVO) {
			throw new IllegalArgumentException("El cliente esta " + cliente.getEstado() + ". Debe estar ACTIVO para ingresar.");
		}

		if (!membresiaRepository.existsByCliente_IdClienteAndEstado(cliente.getIdCliente(), EstadoMembresia.ACTIVA)) {
			throw new IllegalArgumentException("El cliente no tiene una membresia ACTIVA. No puede ingresar.");
		}

		if (asistencia.getHoraSalida() != null && asistencia.getHoraIngreso() != null
				&& asistencia.getHoraSalida().isBefore(asistencia.getHoraIngreso())) {
			throw new IllegalArgumentException("La hora de salida no puede ser anterior a la de ingreso.");
		}
		for (Asistencia a : asistenciaRepository.findByCliente_IdClienteAndFecha(cliente.getIdCliente(), asistencia.getFecha())) {
			if (a.getHoraSalida() == null) {
				throw new IllegalArgumentException("El cliente ya tiene un ingreso abierto hoy (sin salida registrada).");
			}
		}

		asistencia.setCliente(cliente);
		asistenciaRepository.save(asistencia);
	}

	@Override
	public void actualizar(Asistencia asistencia) {
		Asistencia existente = asistenciaRepository.findById(asistencia.getIdAsistencia())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe la asistencia con id: " + asistencia.getIdAsistencia()));
		existente.setFecha(asistencia.getFecha());
		existente.setHoraIngreso(asistencia.getHoraIngreso());
		existente.setHoraSalida(asistencia.getHoraSalida());
		existente.setCliente(resolverCliente(asistencia.getCliente()));
		asistenciaRepository.save(existente);
	}

	@Override
	public void eliminar(Integer id) {
		asistenciaRepository.deleteById(id);
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepository.findClientesActivosConMembresiaActiva();
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
