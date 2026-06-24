package com.gym.service;

import java.util.List;

import com.gym.entity.Instructor;
import com.gym.entity.Usuario;

public interface InstructorService {

	List<Instructor> listar();

	Instructor buscarPorId(Integer id);

	void registrar(Instructor instructor);

	void actualizar(Instructor instructor);

	void desactivar(Integer id); // baja logica: estado = INACTIVO

	List<Usuario> listarUsuarios(); // para el dropdown FK
}
