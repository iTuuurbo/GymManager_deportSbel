package com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.EstadoRegistro;
import com.gym.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

	List<Instructor> findByEstado(EstadoRegistro estado);
}
