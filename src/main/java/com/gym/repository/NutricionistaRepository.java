package com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Nutricionista;

public interface NutricionistaRepository extends JpaRepository<Nutricionista, Integer> {

	List<Nutricionista> findByEstado(byte estado);
}
