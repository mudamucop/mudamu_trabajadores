package com.Mudamu.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "prediccionID", "nombre", "apellido1", "apellido2", "fecha_hora", "categoriaID",
		"nombreCategoria", "medicoID", "citaSolicitada" })
public class Prediccion implements Serializable {
	private static final long serialVersionUID = 1671417246199538663L;

	private Integer prediccionID;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String fecha_hora;
	private Integer categoriaID;
	private String nombreCategoria;
	private Integer medicoID;
	private Integer citaSolicitada;

	public Prediccion() {
		super();
	}

	public Prediccion(Integer prediccionID) {
		super();
		this.prediccionID = prediccionID;
	}

	public Prediccion(Integer prediccionID, String nombre, String apellido1, String apellido2, String fecha_hora,
			Integer categoriaID, String nombreCategoria, Integer medicoID, Integer citaSolicitada) {
		super();
		this.prediccionID = prediccionID;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.fecha_hora = fecha_hora;
		this.categoriaID = categoriaID;
		this.nombreCategoria = nombreCategoria;
		this.medicoID = medicoID;
		this.citaSolicitada = citaSolicitada;
	}

	public Integer getPrediccionID() {
		return prediccionID;
	}

	public void setPrediccionID(Integer prediccionID) {
		this.prediccionID = prediccionID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getFecha_hora() {
		return fecha_hora;
	}

	public void setFecha_hora(String fecha_hora) {
		this.fecha_hora = fecha_hora;
	}

	public Integer getCategoriaID() {
		return categoriaID;
	}

	public void setCategoriaID(Integer categoriaID) {
		this.categoriaID = categoriaID;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public void setMedicoID(Integer medicoID) {
		this.medicoID = medicoID;
	}

	public Integer getMedicoID() {
		return medicoID;
	}

	public Integer getCitaSolicitada() {
		return citaSolicitada;
	}

	public void setCitaSolicitada(Integer citaSolicitada) {
		this.citaSolicitada = citaSolicitada;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "prediccionID: " + prediccionID + " nombre: " + nombre + " apellido1: " + apellido1 + " apellido2: "
				+ apellido2 + " fecha_hora: " + fecha_hora + " categoriaID: " + categoriaID + " nombreCategoria: "
				+ nombreCategoria + " medicoID: " + medicoID + " citaSolicitada: " + citaSolicitada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prediccionID == null) ? 0 : prediccionID.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result + ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime * result + ((fecha_hora == null) ? 0 : fecha_hora.hashCode());
		result = prime * result + ((categoriaID == null) ? 0 : categoriaID.hashCode());
		result = prime * result + ((nombreCategoria == null) ? 0 : nombreCategoria.hashCode());
		result = prime * result + ((medicoID == null) ? 0 : medicoID.hashCode());
		result = prime * result + ((citaSolicitada == null) ? 0 : citaSolicitada.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Prediccion other = (Prediccion) obj;
		if (prediccionID == null) {
			if (other.prediccionID != null)
				return false;
		} else if (!prediccionID.equals(other.prediccionID))
			return false;

		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;

		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;

		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;

		if (fecha_hora == null) {
			if (other.fecha_hora != null)
				return false;
		} else if (!fecha_hora.equals(other.fecha_hora))
			return false;

		if (categoriaID == null) {
			if (other.categoriaID != null)
				return false;
		} else if (!categoriaID.equals(other.categoriaID))
			return false;

		if (nombreCategoria == null) {
			if (other.nombreCategoria != null)
				return false;
		} else if (!nombreCategoria.equals(other.nombreCategoria))
			return false;

		if (medicoID == null) {
			if (other.medicoID != null)
				return false;
		} else if (!medicoID.equals(other.medicoID))
			return false;
		
		if (citaSolicitada == null) {
			if (other.citaSolicitada != null)
				return false;
		} else if (!citaSolicitada.equals(other.citaSolicitada))
			return false;

		return true;
	}

}
