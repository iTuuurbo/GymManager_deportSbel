package com.gym.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name="CitaNutricional")
public class CitaNutricional {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idCita;	
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name="idNutricionista")
	private Nutricionista nutricionista;
	private LocalDate fecha;
	private LocalTime hora;
	@Enumerated(EnumType.STRING)
	private EstadoCita estado;
	private String observaciones;
	public Integer getIdCita() {
		return idCita;
	}
	public void setIdCita(Integer idCita) {
		this.idCita = idCita;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Nutricionista getNutricionista() {
		return nutricionista;
	}
	public void setNutricionista(Nutricionista nutricionista) {
		this.nutricionista = nutricionista;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public EstadoCita getEstado() {
		return estado;
	}
	public void setEstado(EstadoCita estado) {
		this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	
	
}