package com.hpr.web.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.model.Contenido;






public class Carrito {

	private static Logger logger = LogManager.getLogger(Carrito.class.getName());

	private List<LineaCarrito> lineas = null;
	private double total;

	public Carrito() {
		lineas= new ArrayList<LineaCarrito>();
	}

	public List<LineaCarrito> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaCarrito> lineas) {
		this.lineas = lineas;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void anadir(LineaCarrito lp) {
		lineas.add(lp);
	}

	public int numeroLineas() {
		return lineas.size();
	}

	public void deleteLineaCarrito(Integer idContenido) {
		try {
			for (int i=0;i<lineas.size();i++) {
				if (lineas.get(i).getContenido().getIdContenido()==idContenido) {
					lineas.remove(i);
				}
			}
			calculateOrderTotal();	   

		} catch(NumberFormatException e) {

			logger.error("Error al eliminar del carrito: "+ e.getMessage());	   
		}
	}

	public boolean addLineaCarrito(Contenido c) {

		LineaCarrito lineaCarrito = new LineaCarrito();
		boolean checkDuplicate = false;
		try {
			if (!checkDuplicated(c)) {
				checkDuplicate=false;
				lineaCarrito.setContenido(c);
				lineaCarrito.setPrecio(c.getPrecio());
				lineaCarrito.setPrecioDescontado(c.getPrecioDescontado());
				lineas.add(lineaCarrito);
				calculateOrderTotal();
			}else {
				checkDuplicate = true;
			}
		} catch (NumberFormatException e) {
			logger.error("Error al añadir al carrito: "+ e.getMessage());	
		}
		return checkDuplicate;
	}

	public void addLineaCarrito(LineaCarrito lc) {
		lineas.add(lc);
	}

	public LineaCarrito getLineaCarrito(int iItemIndex) {
		LineaCarrito lineaCarrito = null;
		if (lineas.size() > iItemIndex) {
			lineaCarrito = (LineaCarrito) lineas.get(iItemIndex);
		}
		return lineaCarrito;
	}

	protected void calculateOrderTotal() {
		double totalCarrito = 0;
		for(int contador=0;contador<lineas.size(); contador++) {
			LineaCarrito linea = (LineaCarrito) lineas.get(contador);
			totalCarrito+=linea.getPrecio()-linea.getPrecioDescontado();

		}
		setTotal(totalCarrito);
	}

	protected boolean checkDuplicated(Contenido c) {
		boolean repetido=false;

		for (int i=0;i<lineas.size();i++) {
			if (lineas.get(i).getContenido().getIdContenido()==c.getIdContenido()) {
				repetido=true;
			}else {
				repetido=false;
			}
		}

		return repetido;
	}
}
