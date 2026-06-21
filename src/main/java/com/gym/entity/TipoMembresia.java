package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TipoMembresia")
public class TipoMembresia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTipoMembresia")
	private int idTipoMembresia;
	@Column(name = "nombre", nullable = false, length = 50, unique = true)
	private String nombre;
	@Column(name = "duracionDias", nullable = false)
	private int duracionDias;
	@Column(name = "precio", nullable = false, columnDefinition = "double" )
	private double precio;
	@Column(name = "estado", columnDefinition = "TINYINT", nullable = false, length = 1)
	private byte estado;
	
	public TipoMembresia() {
		
	}

	public int getIdTipoMembresia() {
		return idTipoMembresia;
	}

	public void setIdTipoMembresia(int idTipoMembresia) {
		this.idTipoMembresia = idTipoMembresia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDuracionDias() {
		return duracionDias;
	}

	public void setDuracionDias(int duracionDias) {
		this.duracionDias = duracionDias;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public byte getEstado() {
		return estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}
	
	
}
