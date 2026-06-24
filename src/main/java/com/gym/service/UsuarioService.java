package com.gym.service;

import java.util.List;

import com.gym.entity.Rol;
import com.gym.entity.Usuario;

public interface UsuarioService {

	List<Usuario> listar();

	Usuario buscarPorId(Integer id);

	void registrar(Usuario usuario);

	void actualizar(Usuario usuario);

	void desactivar(Integer id); // baja logica: estado = 0

	List<Rol> listarRoles(); // para el dropdown
}
