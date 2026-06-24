package com.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByUsername(String username);
}
