package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Cliente;
import com.gym.entity.EstadoMembresia;
import com.gym.entity.Membresia;
import com.gym.entity.TipoMembresia;
import com.gym.repository.ClienteRepository;
import com.gym.repository.MembresiaRepository;
import com.gym.repository.TipoMembresiaRepository;

@Service
public class MembresiaServiceImpl implements MembresiaService {

	@Autowired
	private MembresiaRepository membresiaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TipoMembresiaRepository tipoMembresiaRepository;

	@Override
	public List<Membresia> listar() {
		return membresiaRepository.findAll();
	}

	@Override
	public Membresia buscarPorId(Integer id) {
		return membresiaRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(Membresia membresia) {
		if (membresia.getEstado() == null) {
			membresia.setEstado(EstadoMembresia.ACTIVA);
		}
		membresia.setCliente(resolverCliente(membresia.getCliente()));
		membresia.setTipoMembresia(resolverTipoMembresia(membresia.getTipoMembresia()));
		membresiaRepository.save(membresia);
	}

	@Override
	public void actualizar(Membresia membresia) {
		Membresia existente = membresiaRepository.findById(membresia.getIdMembresia())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe la membresia con id: " + membresia.getIdMembresia()));
		existente.setFechaInicio(membresia.getFechaInicio());
		existente.setFechaFin(membresia.getFechaFin());
		existente.setEstado(membresia.getEstado());
		existente.setCliente(resolverCliente(membresia.getCliente()));
		existente.setTipoMembresia(resolverTipoMembresia(membresia.getTipoMembresia()));
		membresiaRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		Membresia membresia = membresiaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe la membresia con id: " + id));
		membresia.setEstado(EstadoMembresia.SUSPENDIDA);
		membresiaRepository.save(membresia);
	}

	@Override
	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	@Override
	public List<TipoMembresia> listarTiposMembresia() {
		return tipoMembresiaRepository.findAll();
	}

	private Cliente resolverCliente(Cliente seleccionado) {
		int id = (seleccionado != null) ? seleccionado.getIdCliente() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar un cliente.");
		}
		return clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El cliente seleccionado no existe (id " + id + ")."));
	}

	private TipoMembresia resolverTipoMembresia(TipoMembresia seleccionado) {
		int id = (seleccionado != null) ? seleccionado.getIdTipoMembresia() : 0;
		if (id == 0) {
			throw new IllegalArgumentException("Debe seleccionar un tipo de membresia.");
		}
		return tipoMembresiaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El tipo de membresia no existe (id " + id + ")."));
	}
}
