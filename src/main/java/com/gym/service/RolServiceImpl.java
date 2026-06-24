package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Rol;
import com.gym.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository rolRepository;

	@Override
	public List<Rol> listar() {
		return rolRepository.findAll();
	}

	@Override
	public Rol buscarPorId(Integer id) {
		return rolRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(Rol rol) {
		rolRepository.save(rol);
	}

	@Override
	public void actualizar(Rol rol) {
		Rol existente = rolRepository.findById(rol.getIdRol())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el rol con id: " + rol.getIdRol()));
		existente.setNombre(rol.getNombre());
		existente.setEstado(rol.getEstado());
		rolRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		Rol rol = rolRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el rol con id: " + id));
		rol.setEstado((byte) 0);
		rolRepository.save(rol);
	}

	@Override
	public Rol buscarPorNombre(String nombre) {
		return rolRepository.findByNombre(nombre);
	}
}
