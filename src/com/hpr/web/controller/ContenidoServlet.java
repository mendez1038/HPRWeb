package com.hpr.web.controller;

import java.io.IOException;
import java.util.List;

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
import com.david.training.service.impl.ContenidoServiceImpl;
import com.hpr.web.model.ErrorCodes;
import com.hpr.web.model.Errors;
import com.hpr.web.util.ParameterUtils;
import com.hpr.web.util.ValidationUtils;
@WebServlet("/contenido")
public class ContenidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ContenidoService servicio = null;
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class.getName());


	public ContenidoServlet() {
		servicio = new ContenidoServiceImpl();
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

			String titulo = request.getParameter(ParameterNames.TITULO);

			titulo = ValidationUtils.stringOnlyLettersValidator(titulo, true);
			if (titulo == null) {
				errors.add(ParameterNames.TITULO, ErrorCodes.SEARCH_ERROR);
			}

			ProductoCriteria pc = new ProductoCriteria();
			String idioma = "es";
			pc.setTitulo(titulo);
			List<Contenido> resultados = null;
			try {
				resultados = servicio.busquedaEstructurada(pc, idioma);
			} catch (DataException e) {
				logger.warn("buscando "+pc, e);
				errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);
			}
			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Busqueda fallida: {}", errors);
				}				
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.HOME;				
			} else {			
				if (logger.isDebugEnabled()) {
					logger.info("Busqueda completada para: "+titulo);
				}				
				request.setAttribute(AttributeNames.RESULTADOS, resultados);
				target = ViewPaths.BUSQUEDA;
				redirect=true;
			}
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
