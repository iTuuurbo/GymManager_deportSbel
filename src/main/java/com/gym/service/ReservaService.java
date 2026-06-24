package com.gym.service;

import java.util.List;

import com.gym.entity.ClaseGrupal;
import com.gym.entity.Cliente;
import com.gym.entity.Reserva;

public interface ReservaService {

	List<Reserva> listar();

	Reserva buscarPorId(Integer id);

	void registrar(Reserva reserva); // descuenta cupo de la clase

	void actualizar(Reserva reserva);

	void desactivar(Integer id); // cancela: estado = CANCELADA y devuelve el cupo

	List<ClaseGrupal> listarClases();

	List<Cliente> listarClientes();
}
