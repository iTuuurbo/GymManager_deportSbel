package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Cliente;
import com.gym.entity.EstadoMembresia;
import com.gym.entity.EstadoRegistro;
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
		return actualizarVigencia(membresiaRepository.findAll());
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
		Cliente cliente = resolverCliente(membresia.getCliente());

		if (cliente.getEstado() != EstadoRegistro.ACTIVO) {
			throw new IllegalArgumentException("El cliente esta " + cliente.getEstado() + ". Reactivelo (ACTIVO) antes de asignarle una membresia.");
		}

		for (Membresia m : membresiaRepository.findByCliente_IdCliente(cliente.getIdCliente())) {
			if (m.getEstado() == EstadoMembresia.ACTIVA) {
				throw new IllegalArgumentException("El cliente ya tiene una membresia ACTIVA. Desactivela o suspendala antes de asignar una nueva.");
			}
		}

		if (membresia.getFechaInicio() != null && membresia.getFechaFin() != null
				&& membresia.getFechaFin().isBefore(membresia.getFechaInicio())) {
			throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio.");
		}

		membresia.setCliente(cliente);
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
		return clienteRepository.findByEstado(EstadoRegistro.ACTIVO);
	}

	@Override
	public List<TipoMembresia> listarTiposMembresia() {
		return tipoMembresiaRepository.findByEstado((byte) 1);
	}

	@Override
	public List<Membresia> listarPorCliente(Integer idCliente) {
		return actualizarVigencia(membresiaRepository.findByCliente_IdCliente(idCliente));
	}

	// Recalcula vigencia: marca VENCIDA toda membresia (no suspendida) cuya fechaFin ya paso.
	private List<Membresia> actualizarVigencia(List<Membresia> lista) {
		java.time.LocalDate hoy = java.time.LocalDate.now();
		for (Membresia m : lista) {
			if (m.getEstado() != EstadoMembresia.SUSPENDIDA && m.getEstado() != EstadoMembresia.VENCIDA
					&& m.getFechaFin() != null && m.getFechaFin().isBefore(hoy)) {
				m.setEstado(EstadoMembresia.VENCIDA);
				membresiaRepository.save(m);
			}
		}
		return lista;
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
