package com.gym.service;

import java.util.List;

import com.gym.entity.CitaNutricional;
import com.gym.entity.Cliente;
import com.gym.entity.Nutricionista;

public interface CitaNutricionalService {

	List<CitaNutricional> listar();

	CitaNutricional buscarPorId(Integer id);

	void registrar(CitaNutricional cita);

	void actualizar(CitaNutricional cita);

	void desactivar(Integer id); // baja logica: estado = CANCELADA

	List<Cliente> listarClientes();

	List<Nutricionista> listarNutricionistas();
}
