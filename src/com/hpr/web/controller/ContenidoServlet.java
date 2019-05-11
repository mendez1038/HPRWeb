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
import com.david.training.model.Artista;
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
	
	
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT))+1; 
	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 
	
	private static Logger logger = LogManager.getLogger(ContenidoServlet.class.getName());
	
	
	private ContenidoService servicio = null;


	public ContenidoServlet() {
		super();
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
		boolean hasErrors = false;
		String idioma=SessionManager.get(request,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
		
		
		if (Actions.BUSCAR.equalsIgnoreCase(action)) {
			
			try {		
			String texto = request.getParameter(ParameterNames.TITULO);
			String[] generos = request.getParameterValues(ParameterNames.CATEGORIA);
			String rEdad = request.getParameter(ParameterNames.RESTRICCION_EDAD);
			String tipoContenido = request.getParameter(ParameterNames.TIPO_CONTENIDO);	
//			Integer descuento = null;
//			if(request.getParameter(ParameterNames.DESCUENTO) !="") {
//			descuento = Integer.valueOf(request.getParameter(ParameterNames.DESCUENTO));
//			}
			String nombreArtista = request.getParameter(ParameterNames.ARTISTA);
//			String precio = request.getParameter(ParameterNames.PRECIO);
//			String ano = request.getParameter(ParameterNames.ANO);
			String[] pais = request.getParameterValues(ParameterNames.PAIS);
//			String duracion = request.getParameter(ParameterNames.DURACION);
			
			if (hasErrors) {
				
				target = ViewPaths.INDEX;
			} else {
			
			int page = WebUtils.
					getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
			
			StringBuilder targetString = new StringBuilder();
			ProductoCriteria pc = null;
			Artista a = null;

			
				 pc = new ProductoCriteria();
				 a = new Artista();
				
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
			
				if(nombreArtista!=null) {
 				a.setNombreArtista(nombreArtista);
				pc.setA(a);
				targetString.append("&"+ParameterNames.ARTISTA+"="+a.getNombreArtista());
				}
				if(pais!=null) {
					pc.setPais(ArrayUtils.arrayToPais(pais));
					targetString.append("&"+ParameterNames.PAIS+"="+pc.getPais());
					}
				if(generos!=null) {
					pc.setCategoria(ArrayUtils.arrayToCategoria(generos));
					targetString.append("&"+ParameterNames.CATEGORIA+"="+pc.getCategoria());
					}
				
				Results<Contenido> resultados = servicio.busquedaEstructurada(pc, idioma, (page-1)*pageSize+1, pageSize);
					
					
						
						request.setAttribute(AttributeNames.RESULTADOS, resultados.getPage());
						request.setAttribute(AttributeNames.RESULTADOS_TOTAL, resultados.getTotal());
						
						int totalPages = (int) Math.ceil(resultados.getTotal()/(double)pageSize);
						int firstPagedPage = Math.max(1, page-pagingPageCount);
						int lastPagedPage = Math.min(totalPages, page+pagingPageCount);
						request.setAttribute(ParameterNames.PAGE, page);
						request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
						request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
						request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
						request.setAttribute(ParameterNames.URL, targetString.toString());
						target = ViewPaths.BUSQUEDA;
						logger.info(pc);
			}	
			} catch (DataException e) {
					logger.warn("Buscando... "+ e);
					errors.add(ParameterNames.ACTION,ErrorCodes.SEARCH_ERROR);	
				}

			}
					
		{		
			
			//vista detalle
		}  if(Actions.BUSCAR_ID.equalsIgnoreCase(action)) {
		
			
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
