package com.gym.service;

import java.util.List;

import com.gym.entity.ClaseGrupal;
import com.gym.entity.Instructor;

public interface ClaseGrupalService {

	List<ClaseGrupal> listar();

	ClaseGrupal buscarPorId(Integer id);

	void registrar(ClaseGrupal claseGrupal);

	void actualizar(ClaseGrupal claseGrupal);

	void desactivar(Integer id); // baja logica: estadoGrupal = CANCELADA

	List<Instructor> listarInstructores();
}
