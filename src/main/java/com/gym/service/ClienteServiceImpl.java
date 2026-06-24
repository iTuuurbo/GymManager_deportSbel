package com.gym.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Cliente;
import com.gym.entity.EstadoRegistro;
import com.gym.entity.Rol;
import com.gym.entity.Usuario;
import com.gym.repository.ClienteRepository;
import com.gym.repository.UsuarioRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolService rolService;

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
		cliente.setFechaRegistro(LocalDateTime.now());
		if (cliente.getEstado() == null) {
			cliente.setEstado(EstadoRegistro.ACTIVO);
		}

		Cliente existenteDni = clienteRepository.findByDni(cliente.getDni());
		if (existenteDni != null) {
			throw new IllegalArgumentException("Ya existe un cliente con el DNI: " + cliente.getDni());
		}

		Rol rolCliente = rolService.buscarPorNombre("CLIENTE");
		if (rolCliente == null) {
			throw new IllegalArgumentException("No existe el rol CLIENTE en la base de datos.");
		}

		Usuario nuevo = new Usuario();
		nuevo.setUsername(cliente.getDni());
		nuevo.setPassword(cliente.getDni() + "123");
		nuevo.setEstado((byte) 1);
		nuevo.setIdRol(rolCliente);
		usuarioRepository.save(nuevo);

		cliente.setUsuario(nuevo);
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
		existente.setDirectorioFotoPerfil(cliente.getDirectorioFotoPerfil());

		Cliente otroDni = clienteRepository.findByDni(cliente.getDni());
		if (otroDni != null && otroDni.getIdCliente() != cliente.getIdCliente()) {
			throw new IllegalArgumentException("Ya existe otro cliente con el DNI: " + cliente.getDni());
		}

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
}
