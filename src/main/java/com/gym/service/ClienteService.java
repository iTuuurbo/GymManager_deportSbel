package com.gym.service;

import java.util.List;

import com.gym.entity.Cliente;
import com.gym.entity.Usuario;

// Interfaz: solo se declaran los metodos. La logica va en ClienteServiceImpl.
public interface ClienteService {

	List<Cliente> listar();

	Cliente buscarPorId(Integer id);

	void registrar(Cliente cliente);

	void actualizar(Cliente cliente);

	// Baja logica: cambia el estado a INACTIVO (no borra el registro).
	void desactivar(Integer id);

	// Usuarios para el dropdown (FK del Cliente).
	List<Usuario> listarUsuarios();
}
