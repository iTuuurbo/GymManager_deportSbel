package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Rol")
public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idRol")
	private int idRol;
	@Column(name = "nombre", nullable = false, length = 50, unique = true)
	private String nombre;
	@Column(name = "estado", columnDefinition = "TINYINT", nullable = false, length = 1)
	private byte estado;
	
	public Rol() {
		
	}

	public int getIdRol() {
		return idRol;
	}



	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte getEstado() {
		return estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	
}
