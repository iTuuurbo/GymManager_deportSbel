package com.gym.service;

import java.util.List;

import com.gym.entity.Cliente;
import com.gym.entity.Membresia;
import com.gym.entity.TipoMembresia;

public interface MembresiaService {

	List<Membresia> listar();

	Membresia buscarPorId(Integer id);

	void registrar(Membresia membresia);

	void actualizar(Membresia membresia);

	void desactivar(Integer id); // baja logica: estado = SUSPENDIDA

	List<Cliente> listarClientes();

	List<TipoMembresia> listarTiposMembresia();
}
