package com.gym.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.CitaNutricional;

public interface CitaNutricionalRepository extends JpaRepository<CitaNutricional, Integer> {

	List<CitaNutricional> findByNutricionista_IdNutricionistaAndFecha(Integer idNutricionista, LocalDate fecha);
}
