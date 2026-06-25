package com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.TipoMembresia;

public interface TipoMembresiaRepository extends JpaRepository<TipoMembresia, Integer> {

	List<TipoMembresia> findByEstado(byte estado);
}
