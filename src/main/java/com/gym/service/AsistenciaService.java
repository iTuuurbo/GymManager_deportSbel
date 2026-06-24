package com.gym.service;

import java.util.List;

import com.gym.entity.Asistencia;
import com.gym.entity.Cliente;

public interface AsistenciaService {

	List<Asistencia> listar();

	Asistencia buscarPorId(Integer id);

	void registrar(Asistencia asistencia);

	void actualizar(Asistencia asistencia);

	void eliminar(Integer id); // Asistencia no tiene estado -> borrado fisico

	List<Cliente> listarClientes();
}
