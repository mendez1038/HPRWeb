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
import com.david.training.service.CategoriaService;
import com.david.training.service.ContenidoService;
import com.david.training.service.PaisService;
import com.david.training.service.Results;
import com.david.training.service.TipoContenidoService;
import com.david.training.service.impl.CategoriaServiceImpl;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.david.training.service.impl.PaisServiceImpl;
import com.david.training.service.impl.TipoContenidoServiceImpl;
import com.hpr.web.model.ErrorCodes;
import com.hpr.web.model.Errors;
import com.hpr.web.util.ValidationUtils;
import com.mysql.cj.util.StringUtils;
@WebServlet("/contenido")
public class ContenidoServlet extends HttpServlet {
	

	private static Logger logger = LogManager.getLogger(UsuarioServlet.class.getName());
	
	private ContenidoService servicio = null;
//	private CategoriaService servicioCategoria = null;
//	private PaisService servicioPais = null;
//	private TipoContenidoService servicioTipo = null;
	


	public ContenidoServlet() {
		super();
		servicio = new ContenidoServiceImpl();
//		servicioCategoria = new CategoriaServiceImpl();
//		servicioPais = new PaisServiceImpl();
//		servicioTipo = new TipoContenidoServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String action = request.getParameter(ParameterNames.ACTION);

		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}

		Errors errors = new Errors(); 
		String target = null;
		boolean redirect = false;

		if (Actions.BUSCAR.equalsIgnoreCase(action)) {

			String texto = request.getParameter(ParameterNames.TITULO);
//			String[] generos = request.getParameterValues(ParameterNames.GENERO);
//			String rEdad = request.getParameter(ParameterNames.RESTRICCION_EDAD);
//			String tipoContenido = request.getParameter(ParameterNames.TIPO_CONTENIDO);
//			String descuento = request.getParameter(ParameterNames.DESCUENTO);
//			String artista = request.getParameter(ParameterNames.ARTISTA);
//			String precio = request.getParameter(ParameterNames.PRECIO);
//			String ano = request.getParameter(ParameterNames.ANO);
//			String[] pais = request.getParameterValues(ParameterNames.PAIS);
//			String duracion = request.getParameter(ParameterNames.DURACION);

			

			ProductoCriteria pc = new ProductoCriteria();
				
			if(texto!=null && !StringUtils.isEmptyOrWhitespaceOnly(texto)) {
				texto = ValidationUtils.stringOnlyLettersValidator(texto, false);
				pc.setTitulo(texto);
				}

				Results<Contenido> resultados = null;
				try {
					resultados = servicio.busquedaEstructurada(pc, "es", 1, 3);
				} catch (DataException e) {
					logger.warn("Buscando... "+texto, e);
					errors.add(ParameterNames.ACTION,ErrorCodes.SEARCH_ERROR);	
				}


				if (errors.hasErrors()) {	
					if (logger.isDebugEnabled()) {
						logger.debug("Busqueda fallida: {}", errors);
					}				
					request.setAttribute(AttributeNames.ERRORS, errors);				
					target = ViewPaths.HOME;				
				} else {			
					if (logger.isDebugEnabled()) {
						logger.info("Busqueda completada para: "+texto);
					}				
					request.setAttribute(AttributeNames.RESULTADOS, resultados);
					target = request.getContextPath()+ViewPaths.BUSQUEDA;
					redirect=true;
				}
			
			//vista detalle
		} else if(Actions.BUSCAR_ID.equalsIgnoreCase(action)) {
		
			String idParamValue = request.getParameter(ParameterNames.ID);
			Integer id = Integer.valueOf(idParamValue);

			Contenido contenidoDetalle = new Contenido();
			try {				
				contenidoDetalle = servicio.findById(id, "es");
				if (contenidoDetalle==null ) {
					request.setAttribute(AttributeNames.ERRORS, errors);
					target = ViewPaths.HOME;
				} else {	
					target = ViewPaths.VISTA_DETALLE;
					request.setAttribute(SessionAttributeNames.CONTENIDO, contenidoDetalle);
		
				}
					request.getRequestDispatcher(target).forward(request, response);
			} catch (DataException e) {
				logger.warn("Detalle... "+id, e);
				errors.add(ParameterNames.ACTION,ErrorCodes.DETALLE_ERROR);
			}
//			request.setAttribute(AttributeNames.CONTENIDO, contenidoDetalle);

		
		} else {
			// Mmm...
			logger.error("Action desconocida");
			target = ViewPaths.HOME;
		}

		if (redirect) {
			logger.info("Redirecting to "+target);
			response.sendRedirect(target);
		} else {
			logger.info("Forwarding to "+target);
			request.getRequestDispatcher(target).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
