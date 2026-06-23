package com.gym.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Cliente;
import com.gym.entity.EstadoRegistro;
import com.gym.entity.Usuario;
import com.gym.repository.ClienteRepository;
import com.gym.repository.UsuarioRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Integer id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(Cliente cliente) {
		// La entidad no tiene @CreationTimestamp y la columna es NOT NULL,
		// asi que la fecha de registro se setea aqui al crear.
		cliente.setFechaRegistro(LocalDateTime.now());
		if (cliente.getEstado() == null) {
			cliente.setEstado(EstadoRegistro.ACTIVO);
		}
		// Cargamos el Usuario administrado desde la BD para evitar el
		// "transient instance" al guardar (el form solo trae el id).
		cliente.setUsuario(resolverUsuario(cliente.getUsuario()));
		clienteRepository.save(cliente);
	}

	@Override
	public void actualizar(Cliente cliente) {
		// Traemos el existente para conservar la fechaRegistro (el form no la envia)
		// y copiamos solo los campos editables.
		Cliente existente = clienteRepository.findById(cliente.getIdCliente())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el cliente con id: " + cliente.getIdCliente()));

		existente.setDni(cliente.getDni());
		existente.setNombres(cliente.getNombres());
		existente.setApellidos(cliente.getApellidos());
		existente.setTelefono(cliente.getTelefono());
		existente.setEmail(cliente.getEmail());
		existente.setFechaNacimiento(cliente.getFechaNacimiento());
		existente.setEstado(cliente.getEstado());
		existente.setUsuario(resolverUsuario(cliente.getUsuario())); // Usuario administrado -> actualiza la FK

		clienteRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el cliente con id: " + id));
		cliente.setEstado(EstadoRegistro.INACTIVO);
		clienteRepository.save(cliente);
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	/**
	 * Trae el Usuario administrado desde la BD a partir del id que viene del
	 * formulario. Si no se selecciono uno (id 0) o no existe, lanza un error
	 * controlado para mostrar un mensaje al usuario en vez de un 500.
	 */
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
