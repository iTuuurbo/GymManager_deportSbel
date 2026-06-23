package com.gym.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Membresia")
public class Membresia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMembresia")
	private int idMembresia;
	@ManyToOne
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "idTipoMembresia", nullable = false)
	private TipoMembresia tipoMembresia;
	@Column(name = "fechaInicio", nullable = false)
	private LocalDate fechaInicio;
	@Column(name = "fechaFin", nullable = false)
	private LocalDate fechaFin;
	//ENUM
	@Enumerated(EnumType.STRING) // Guarda "ACTIVA", "PROXIMA_A_VENCER", "VENCIDA", "SUSPENDIDA"
	@Column(name = "estado", nullable = false)
	private EstadoMembresia estado;

	
	public Membresia() {
		
	}

	public int getIdMembresia() {
		return idMembresia;
	}

	public void setIdMembresia(int idMembresia) {
		this.idMembresia = idMembresia;
	}

	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoMembresia getTipoMembresia() {
		return tipoMembresia;
	}

	public void setTipoMembresia(TipoMembresia tipoMembresia) {
		this.tipoMembresia = tipoMembresia;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public EstadoMembresia getEstado() {
		return estado;
	}

	public void setEstado(EstadoMembresia estado) {
		this.estado = estado;
	}
	

}
