package com.hpr.web.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.exceptions.DataException;
import com.david.training.model.Contenido;

import com.david.training.model.ProductoCriteria;
import com.david.training.service.ContenidoService;
import com.david.training.service.Results;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.hpr.web.config.ConfigurationManager;
import com.hpr.web.config.ConfigurationParameterNames;
import com.hpr.web.model.ErrorCodes;
import com.hpr.web.model.Errors;
import com.hpr.web.util.ArrayUtils;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;
import com.hpr.web.util.WebUtils;

@WebServlet("/contenido")
public class ContenidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private static Logger logger = LogManager.getLogger(ContenidoServlet.class.getName());
	private ContenidoService servicio = null;
	//private FavoritoService servicioFavoritos = null;
	//private CompraService serviciocompra = null;

	public ContenidoServlet() {
		super();
		servicio = new ContenidoServiceImpl();
		//servicioFavoritos = new FavoritoServiceImpl();
		//servicioCompre = new CompraServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//servlet parameters
		String action = request.getParameter(ParameterNames.ACTION);
		String target = null;
		boolean redirect = false;
		String idioma=null;

		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}
		
		//Objetos
		Errors errors = new Errors();
		StringBuilder targetString = new StringBuilder();
		ProductoCriteria pc = null;
		Contenido contenidoDetalle = null;

		//listas
		Results<Contenido> resultados = null;

		//parametros
		String texto = null;
		String[] generos = null;
		String rEdad = null;
		String tipoContenido = null;	
		Integer descuento = null;
		String ano = null;
		String idContenido = null;
		Integer id = null;


		if (Actions.BUSCAR.equalsIgnoreCase(action)) {
			//NO FUNCIONA
			pc = new ProductoCriteria();

			texto = request.getParameter(ParameterNames.TITULO).trim();
			
			//generos = request.getParameterValues(ParameterNames.CATEGORIA);
			//rEdad = request.getParameter(ParameterNames.RESTRICCION_EDAD);
			//tipoContenido = request.getParameter(ParameterNames.TIPO_CONTENIDO);	
			//descuento = null;
			//if(request.getParameter(ParameterNames.DESCUENTO) !="") {
			//descuento = Integer.valueOf(request.getParameter(ParameterNames.DESCUENTO));
			//}
			//ano = request.getParameter(ParameterNames.ANO);

		



				if(texto!=null && texto!="") {
					pc.setTitulo(texto);
					targetString.append("&"+ParameterNames.TITULO+"="+pc.getTitulo());
				} else {
					errors.add(ParameterNames.ACTION,ErrorCodes.SEARCH_ERROR);
				}
				if(rEdad!=null) {
					pc.setRestriccionEdad(rEdad);;
					targetString.append("&"+ParameterNames.RESTRICCION_EDAD+"="+pc.getRestriccionEdad());
				}
				if(tipoContenido!=null) {
					pc.setTipoContenido(tipoContenido);
					targetString.append("&"+ParameterNames.TIPO_CONTENIDO+"="+pc.getTipoContenido());
				}
				//if(descuento!=null) {
				//pc.setPorcentaje(descuento);;
				//targetString.append("&"+ParameterNames.DESCUENTO+"="+pc.getPorcentaje());
				//}

				if(generos!=null) {
					pc.setCategoria(ArrayUtils.arrayToCategoria(generos));
					targetString.append("&"+ParameterNames.CATEGORIA+"="+pc.getCategoria());
				}
				if (errors.hasErrors()) {

					target = ViewPaths.INDEX;
				} else {
					try {
						resultados = servicio.busquedaEstructurada(pc, idioma, 1, 10);
					} catch (DataException e) {
						logger.warn("Busqueda... ", e);
						errors.add(ParameterNames.ACTION,ErrorCodes.SEARCH_ERROR);
					}
					request.setAttribute(ParameterNames.URL, targetString.toString());
					request.setAttribute(AttributeNames.RESULTADOS, resultados);
					target = ViewPaths.BUSQUEDA;
					logger.info(pc);

			}		

			//vista detalle
		}else  if(Actions.BUSCAR_ID.equalsIgnoreCase(action)) {

			idioma = SessionManager.get(request, WebConstants.USER_LOCALE).toString();
			idContenido =  request.getParameter(ParameterNames.ID);
			id = Integer.valueOf(idContenido);

			
			try {		
				contenidoDetalle = servicio.findById(id, idioma);
			} catch (Exception e) {
				logger.warn("Detalle... "+id, e);
				errors.add(ParameterNames.ACTION,ErrorCodes.DETALLE_ERROR);

			}
		
			if(errors.hasErrors()) {
				target = ViewPaths.HOME;
				redirect = false;

			} else {
				request.setAttribute(AttributeNames.CONTENIDO, contenidoDetalle);
				target = ViewPaths.VISTA_DETALLE;
				redirect = true;
			}
		} else {
			logger.error("Action desconocida");
			target = ViewPaths.HOME;
		}
		if (redirect) {
			// logger ...
			response.sendRedirect(target);
		} else {
			// logger ...
			request.getRequestDispatcher(target).forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
