package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Asistencia;
import com.gym.entity.Cliente;
import com.gym.repository.AsistenciaRepository;
import com.gym.repository.ClienteRepository;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

	@Autowired
	private AsistenciaRepository asistenciaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

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
		asistencia.setCliente(resolverCliente(asistencia.getCliente()));
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
		return clienteRepository.findAll();
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
