package com.hpr.web.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.david.training.model.Artista;
import com.david.training.model.Categoria;
import com.david.training.model.Contenido;
import com.david.training.model.Pais;
import com.david.training.model.ProductoCriteria;
import com.david.training.service.CategoriaService;
import com.david.training.service.ContenidoService;
import com.david.training.service.Results;
import com.david.training.service.TipoContenidoService;
import com.david.training.service.impl.CategoriaServiceImpl;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.david.training.service.impl.TipoContenidoServiceImpl;
import com.hpr.web.model.ErrorCodes;
import com.hpr.web.model.Errors;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;

@WebServlet("/contenido")
public class ContenidoServlet extends HttpServlet {
	

	private static Logger logger = LogManager.getLogger(ContenidoServlet.class.getName());
	
	private ContenidoService servicio = null;
	private CategoriaService servicioCategoria = null;
//	private PaisService servicioPais = null;
	private TipoContenidoService servicioTipo = null;
	


	public ContenidoServlet() {
		super();
		servicio = new ContenidoServiceImpl();
		servicioCategoria = new CategoriaServiceImpl();
//		servicioPais = new PaisServiceImpl();
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
		String idioma=SessionManager.get(request,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();

		if (Actions.BUSCAR.equalsIgnoreCase(action)) {
			
			StringBuilder targetString = new StringBuilder();			
			String texto = request.getParameter(ParameterNames.TITULO);
			String[] generos = request.getParameterValues(ParameterNames.CATEGORIA);
			String rEdad = request.getParameter(ParameterNames.RESTRICCION_EDAD);

			String tipoContenido = request.getParameter(ParameterNames.TIPO_CONTENIDO);			
//			Integer descuento = Integer.valueOf(request.getParameter(ParameterNames.DESCUENTO));
			String nombreArtista = request.getParameter(ParameterNames.ARTISTA);
//			String precio = request.getParameter(ParameterNames.PRECIO);
//			String ano = request.getParameter(ParameterNames.ANO);
			//String[] pais = request.getParameterValues(ParameterNames.PAIS);
//			String duracion = request.getParameter(ParameterNames.DURACION);

			Results<Contenido> resultados = null;
			ProductoCriteria pc = new ProductoCriteria();
			Artista a = new Artista();
//			List<Categoria> categorias = new ArrayList<Categoria>();
//			
//			List<Pais> paises = new ArrayList();
			
			int startIndex = 1; 
			int pageSize = 5;
			try {
				if(texto!=null && texto!="") {
				pc.setTitulo(texto);
				targetString.append("&"+ParameterNames.TITULO+"="+pc.getTitulo());
				}
				if(rEdad!=null) {
				pc.setRestriccionEdad(rEdad);;
				targetString.append("&"+ParameterNames.RESTRICCION_EDAD+"="+pc.getRestriccionEdad());
				}
				if(tipoContenido!=null) {
				pc.setTipoContenido(tipoContenido);
				targetString.append("&"+ParameterNames.TIPO_CONTENIDO+"="+pc.getTipoContenido());
				}
//				if(descuento!=null) {
//				pc.setPorcentaje(descuento);;
//				targetString.append("&"+ParameterNames.DESCUENTO+"="+pc.getPorcentaje());
//				}
			
				if(nombreArtista!=null && nombreArtista!="") {
 				a.setNombreArtista(nombreArtista);
				pc.setA(a);;
				targetString.append("&"+ParameterNames.ARTISTA+"="+a.getNombreArtista());
				}
			
			if (hasErrors) {
				
				target = ViewPaths.INDEX;
			} else {
				
				resultados = servicio.busquedaEstructurada(pc, idioma, startIndex, pageSize);
					
					if ( resultados!=null) {
						
						request.setAttribute(AttributeNames.RESULTADOS, resultados.getPage());
					}
					else {
						request.setAttribute(AttributeNames.ERRORS, "No hay resultados");
					}
			}
				} catch (DataException e) {
					logger.warn("Buscando... "+texto, e);
					errors.add(ParameterNames.ACTION,ErrorCodes.SEARCH_ERROR);	
				}


				if (errors.hasErrors()) {	
					if (logger.isDebugEnabled()) {
						logger.debug("Busqueda fallida: {}", errors);
					}				
					request.setAttribute(AttributeNames.ERRORS, errors);				
					target = ViewPaths.INDEX;				
				} else {			
					if (logger.isDebugEnabled()) {
						logger.info("Busqueda completada para: "+pc);
					}				
					
					SessionManager.set(request, SessionAttributeNames.URL, targetString.toString());
					target = ViewPaths.BUSQUEDA;
					
				}
			
			//vista detalle
		} else if(Actions.BUSCAR_ID.equalsIgnoreCase(action)) {
		
			
			Integer idContendio = Integer.valueOf(request.getParameter(ParameterNames.ID));
			
			Contenido contenidoDetalle = new Contenido();
			try {				
				contenidoDetalle = servicio.findById(idContendio, idioma);
				if (contenidoDetalle==null ) {
					request.setAttribute(AttributeNames.ERRORS, errors);
					target = ViewPaths.HOME;
				} else {	
					
					request.setAttribute(SessionAttributeNames.CONTENIDO, contenidoDetalle);
					target = ViewPaths.VISTA_DETALLE;
				}
				
			} catch (DataException e) {
				logger.warn("Detalle... "+idContendio, e);
				errors.add(ParameterNames.ACTION,ErrorCodes.DETALLE_ERROR);
				target = ViewPaths.INDEX;
			}

		
		} else {
			// Mmm...
			logger.error("Action desconocida");
			target = ViewPaths.INDEX;
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
