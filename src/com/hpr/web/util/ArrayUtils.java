/**
 * 
 */
package com.hpr.web.util;

import java.util.ArrayList;
import java.util.List;

import com.david.training.model.Categoria;
import com.david.training.model.LineaPedido;
import com.david.training.model.Pais;
import com.hpr.web.model.LineaCarrito;


public  class ArrayUtils {

	public static List<Categoria> arrayToCategoria(String [] arrayParameter){

		String[] categoria = arrayParameter;	
		Categoria iterator = new Categoria ();
		List<Categoria> categorias = new ArrayList<Categoria>();
		for (String c: categoria) {
			iterator.setIdCategoria(Integer.valueOf(c));

			categorias.add(iterator);
		}
		return categorias;
	}

	public static List<Pais> arrayToPais(String [] arrayParameter){

		String[] idioma = arrayParameter;	
		Pais iterator = new Pais ();
		List<Pais> listaIdiomas = new ArrayList<Pais>();
		for (String c: idioma) {
			iterator.setIdPais(Integer.valueOf(c));
			listaIdiomas.add(iterator);
		}
		return listaIdiomas;
	}


	public static List<LineaPedido> carritoToTicket(List<LineaCarrito> lineasCarrito) {

		List<LineaPedido> lineas = new ArrayList<LineaPedido>();
		LineaPedido lineaTicket = new LineaPedido();
		for (LineaCarrito iterador : lineasCarrito) {
			lineaTicket.setIdContenido(iterador.getContenido().getIdContenido());
			lineaTicket.setPrecioUnidad(iterador.getContenido().getPrecio());
			lineas.add(lineaTicket);
			lineaTicket=new LineaPedido();
		}
		return lineas;
	}
}
