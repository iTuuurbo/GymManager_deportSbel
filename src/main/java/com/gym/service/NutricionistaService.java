package com.gym.service;

import java.util.List;

import com.gym.entity.Nutricionista;
import com.gym.entity.Usuario;

public interface NutricionistaService {

	List<Nutricionista> listar();

	Nutricionista buscarPorId(Integer id);

	void registrar(Nutricionista nutricionista);

	void actualizar(Nutricionista nutricionista);

	void desactivar(Integer id); // baja logica: estado = 0

	List<Usuario> listarUsuarios();
}
