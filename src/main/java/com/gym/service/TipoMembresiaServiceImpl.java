package com.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.TipoMembresia;
import com.gym.repository.TipoMembresiaRepository;

@Service
public class TipoMembresiaServiceImpl implements TipoMembresiaService {

	@Autowired
	private TipoMembresiaRepository tipoMembresiaRepository;
	
	@Override
	public List<TipoMembresia> listar() {
		return tipoMembresiaRepository.findAll();
	}
	

	@Override
	public TipoMembresia buscarPorId(Integer id) {
		return tipoMembresiaRepository.findById(id).orElse(null);
	}

	@Override
	public void registrar(TipoMembresia tipoMembresia) {
		tipoMembresiaRepository.save(tipoMembresia);
	}

	@Override
	public void actualizar(TipoMembresia tipoMembresia) {
		TipoMembresia existente = tipoMembresiaRepository.findById(tipoMembresia.getIdTipoMembresia())
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el tipo de membresia con id: " + tipoMembresia.getIdTipoMembresia()));
		existente.setNombre(tipoMembresia.getNombre());
		existente.setDuracionDias(tipoMembresia.getDuracionDias());
		existente.setPrecio(tipoMembresia.getPrecio());
		existente.setEstado(tipoMembresia.getEstado());
		tipoMembresiaRepository.save(existente);
	}

	@Override
	public void desactivar(Integer id) {
		TipoMembresia tipoMembresia = tipoMembresiaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe el tipo de membresia con id: " + id));
		tipoMembresia.setEstado((byte) 0);
		tipoMembresiaRepository.save(tipoMembresia);
	}
}
