package com.gym.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Asistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

	List<Asistencia> findByCliente_IdClienteAndFecha(Integer idCliente, LocalDate fecha);
}
