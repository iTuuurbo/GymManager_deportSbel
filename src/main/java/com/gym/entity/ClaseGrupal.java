package com.gym.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "ClaseGrupal")
public class ClaseGrupal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idClase")
	private int idClase;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idInstructor", nullable = false)
    private Instructor instructor;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
	@Column(name = "descripcion", length = 250)
	private String descripcion;
	@Column(name = "capacidad", nullable = false)
	private int capacidad;
	@Column(name = "cuposDisponibles", nullable = false)
	private int cuposDisponibles;
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;
	@Column(name = "horaInicio", nullable = false)
	private LocalTime horaInicio;
	@Column(name = "horaFin", nullable = false)
	private LocalTime horaFin;
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	private EstadoGrupal estadoGrupal;
	
	public ClaseGrupal() {
		
	}

	public int getIdClase() {
		return idClase;
	}

	public void setIdClase(int idClase) {
		this.idClase = idClase;
	}

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getCuposDisponibles() {
		return cuposDisponibles;
	}

	public void setCuposDisponibles(int cuposDisponibles) {
		this.cuposDisponibles = cuposDisponibles;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public EstadoGrupal getEstadoGrupal() {
		return estadoGrupal;
	}

	public void setEstadoGrupal(EstadoGrupal estadoGrupal) {
		this.estadoGrupal = estadoGrupal;
	}
	
	

}
