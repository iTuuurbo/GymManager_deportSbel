package com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Membresia;

public interface MembresiaRepository extends JpaRepository<Membresia, Integer>{

	List<Membresia> findByCliente_IdCliente(Integer idCliente);
}
