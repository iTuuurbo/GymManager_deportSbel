package com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>{

	List<Reserva> findByClaseGrupal_IdClaseAndCliente_IdCliente(Integer idClase, Integer idCliente);

	List<Reserva> findByClaseGrupal_IdClase(Integer idClase);
}
