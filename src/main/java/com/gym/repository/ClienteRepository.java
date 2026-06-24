package com.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Cliente;
import com.gym.entity.EstadoRegistro;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	Cliente findByDni(String dni);

	List<Cliente> findByEstado(EstadoRegistro estado);
}
