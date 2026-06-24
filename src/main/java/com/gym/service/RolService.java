package com.gym.service;

import java.util.List;

import com.gym.entity.Rol;

public interface RolService {

	List<Rol> listar();

	Rol buscarPorId(Integer id);

	void registrar(Rol rol);

	void actualizar(Rol rol);

	void desactivar(Integer id); // baja logica: estado = 0
}
