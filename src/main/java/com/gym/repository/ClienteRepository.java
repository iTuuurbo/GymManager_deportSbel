package com.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
