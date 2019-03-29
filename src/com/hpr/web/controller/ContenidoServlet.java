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
import com.david.training.model.Usuario;
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
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.ValidationUtils;
@WebServlet("/contenido")
public class ContenidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ContenidoService servicio = null;
	private CategoriaService servicioCategoria = null;
	private PaisService servicioPais = null;
	private TipoContenidoService servicioTipo = null;
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class.getName());


	public ContenidoServlet() {
		servicio = new ContenidoServiceImpl();
		servicioCategoria = new CategoriaServiceImpl();
		servicioPais = new PaisServiceImpl();
		servicioTipo = new TipoContenidoServiceImpl();
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
		boolean hasErrors = false;			
		Usuario user = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

		if (Actions.BUSCAR.equalsIgnoreCase(action)) {

			String titulo = request.getParameter(ParameterNames.TITULO);

			String tituloValid = ValidationUtils.stringOnlyLettersValidator(titulo, true);
			if (tituloValid == null) {
				errors.add(ParameterNames.TITULO, ErrorCodes.SEARCH_ERROR);
			}

			if (hasErrors) {

				target = ViewPaths.HOME;
			} else {


				ProductoCriteria pc = new ProductoCriteria();
				if(tituloValid!=null && tituloValid!="") {
					pc.setTitulo(tituloValid);
				}

				Results<Contenido> resultados = null;
				try {
					resultados = servicio.busquedaEstructurada(pc, "es", 1, 3);
				} catch (DataException e) {
					logger.warn("Buscando... "+titulo, e);
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
						logger.info("Busqueda completada para: "+titulo);
					}				
					request.setAttribute(AttributeNames.RESULTADOS, resultados);
					target = request.getContextPath()+ViewPaths.BUSQUEDA;
					redirect=true;
				}
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
