package com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.ClaseGrupal;

public interface ClaseGrupalRepository extends JpaRepository<ClaseGrupal, Integer>{

	List<ClaseGrupal> findByInstructor_IdInstructorAndFecha(Integer idInstructor, java.time.LocalDate fecha);
}
