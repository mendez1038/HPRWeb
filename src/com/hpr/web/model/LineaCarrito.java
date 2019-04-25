package com.hpr.web.model;

import com.david.training.model.Contenido;

public class LineaCarrito {
	
	private Contenido contenido = null;
	private double precio;
	
	public LineaCarrito() {
		// TODO Auto-generated constructor stub
	}

	public Contenido getContenido() {
		return contenido;
	}

	public void setContenido(Contenido contenido) {
		this.contenido = contenido;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
