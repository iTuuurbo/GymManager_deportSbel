package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Nutricionista")
public class Nutricionista {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idNutricionista")
	private int idNutricionista;
    @OneToOne(optional = false)
    @JoinColumn(name = "idUsuario", nullable = false, unique = true)
    private Usuario usuario;
	@Column(name = "dni", nullable = false, unique = true, length = 8)
	private String dni;
	@Column(name = "nombres", nullable = false, length = 100)
	private String nombres;
	@Column(name = "apellidos", nullable = false, length = 150)
	private String apellidos;
	@Column(name = "colegiatura", length = 20)
	private String colegiatura;
	@Column(name = "telefono", length = 15)
	private String telefono;
	@Column(name = "estado", columnDefinition = "TINYINT", nullable = false, length = 1)
	private byte estado;
	@Column(name = "directorioFotoPerfil", length = 255)
	private String directorioFotoPerfil;

	public Nutricionista() {

	}

	public String getDirectorioFotoPerfil() {
		return directorioFotoPerfil;
	}

	public void setDirectorioFotoPerfil(String directorioFotoPerfil) {
		this.directorioFotoPerfil = directorioFotoPerfil;
	}

	public int getIdNutricionista() {
		return idNutricionista;
	}

	public void setIdNutricionista(int idNutricionista) {
		this.idNutricionista = idNutricionista;
	}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getColegiatura() {
		return colegiatura;
	}

	public void setColegiatura(String colegiatura) {
		this.colegiatura = colegiatura;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public byte getEstado() {
		return estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	
}
