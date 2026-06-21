package com.gym.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Asistencia")
	public class Asistencia {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idAsistencia;
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;
	@Column(name = "horaIngreso", nullable = false)
	private LocalTime horaIngreso;
	@Column(name = "horaSalida")
	private LocalTime horaSalida;

	public Asistencia() {
		
	}

	public Integer getIdAsistencia() {
		return idAsistencia;
	}

	public void setIdAsistencia(Integer idAsistencia) {
		this.idAsistencia = idAsistencia;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraIngreso() {
		return horaIngreso;
	}

	public void setHoraIngreso(LocalTime horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	public LocalTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalTime horaSalida) {
		this.horaSalida = horaSalida;
	}



}