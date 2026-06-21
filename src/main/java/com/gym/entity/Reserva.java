package com.gym.entity;

import java.time.LocalDateTime;

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
@Table(name = "Reserva")
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idReserva")
	private int idReserva;
	@ManyToOne
	@JoinColumn(name = "idClase")
	private ClaseGrupal idClaseGrupal;
	@ManyToOne
	@JoinColumn(name = "idCliente")
	private Cliente idCliente;
	@Column(name = "fechaReserva")
	private LocalDateTime fechaReserva;
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	private EstadoReserva estadoReserva;
	
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public ClaseGrupal getIdClaseGrupal() {
		return idClaseGrupal;
	}
	public void setIdClaseGrupal(ClaseGrupal idClaseGrupal) {
		this.idClaseGrupal = idClaseGrupal;
	}
	public Cliente getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}
	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(LocalDateTime fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public EstadoReserva getEstadoReserva() {
		return estadoReserva;
	}
	public void setEstadoReserva(EstadoReserva estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

}
