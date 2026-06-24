package com.gym.service;

import java.util.List;

import com.gym.entity.TipoMembresia;

public interface TipoMembresiaService {

	List<TipoMembresia> listar();

	TipoMembresia buscarPorId(Integer id);

	void registrar(TipoMembresia tipoMembresia);

	void actualizar(TipoMembresia tipoMembresia);

	void desactivar(Integer id); // baja logica: estado = 0
}
