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
					ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 
	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 

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
		//Objetos
		Errors errors = new Errors();
		StringBuilder targetString = new StringBuilder();
		ProductoCriteria pc = null;
		Artista a = null;
		Contenido contenidoDetalle = null;

		//listas
		Results<Contenido> resultados = null;

		//parametros
		String texto = null;
		String[] generos = null;
		String rEdad = null;
		String tipoContenido = null;	
		Integer descuento = null;
		String nombreArtista = null;
		String precio = null;
		String ano = null;
		String[] pais = null;
		String duracion = null;
		String idContenido = null;
		Integer id = null;



		//paginacion
		int page;
		int totalPages;
		int firstPagedPage;
		int lastPagedPage;

		//servlet parameters
		String target = null;
		boolean redirect = false;
		String action = request.getParameter(ParameterNames.ACTION);
		String idioma=null;

		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}



		if (Actions.BUSCAR.equalsIgnoreCase(action)) {
			//NO FUNCIONA
			pc = new ProductoCriteria();
			a = new Artista();

			texto = request.getParameter(ParameterNames.TITULO);
			generos = request.getParameterValues(ParameterNames.CATEGORIA);
			rEdad = request.getParameter(ParameterNames.RESTRICCION_EDAD);
			tipoContenido = request.getParameter(ParameterNames.TIPO_CONTENIDO);	
			descuento = null;
			//if(request.getParameter(ParameterNames.DESCUENTO) !="") {
			//descuento = Integer.valueOf(request.getParameter(ParameterNames.DESCUENTO));
			//}
			nombreArtista = request.getParameter(ParameterNames.ARTISTA);
			precio = request.getParameter(ParameterNames.PRECIO);
			ano = request.getParameter(ParameterNames.ANO);
			pais = request.getParameterValues(ParameterNames.PAIS);
			duracion = request.getParameter(ParameterNames.DURACION);

			if (errors.hasErrors()) {

				target = ViewPaths.INDEX;
			} else {

				page = WebUtils.
						getPageNumber(request.getParameter(ParameterNames.PAGE), 1);


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
					//if(descuento!=null) {
					//pc.setPorcentaje(descuento);;
					//targetString.append("&"+ParameterNames.DESCUENTO+"="+pc.getPorcentaje());
					//}

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

					resultados = servicio.busquedaEstructurada(pc, idioma, (page-1)*pageSize+1, pageSize);


					request.setAttribute(AttributeNames.RESULTADOS, resultados.getPage());
					request.setAttribute(AttributeNames.RESULTADOS_TOTAL, resultados.getTotal());

					totalPages = (int) Math.ceil(resultados.getTotal()/(double)pageSize);
					firstPagedPage = Math.max(1, page-pagingPageCount);
					lastPagedPage = Math.min(totalPages, page+pagingPageCount);
					request.setAttribute(ParameterNames.PAGE, page);
					request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
					request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
					request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
					request.setAttribute(ParameterNames.URL, targetString.toString());
					target = ViewPaths.BUSQUEDA;
					logger.info(pc);
				} catch (DataException e) {
					logger.warn("Buscando... "+ e);
					errors.add(ParameterNames.ACTION,ErrorCodes.SEARCH_ERROR);	
				}

			}		

			//vista detalle
		}else  if(Actions.BUSCAR_ID.equalsIgnoreCase(action)) {

			idioma = SessionManager.get(request, WebConstants.USER_LOCALE).toString();
			idContenido =  request.getParameter(ParameterNames.ID);
			id = Integer.valueOf(idContenido);


			/*if(idContenido != null) {
				*/try {		
					contenidoDetalle = servicio.findById(id, idioma);
				} catch (Exception e) {
					logger.warn("Detalle... "+idContenido, e);
					errors.add(ParameterNames.ACTION,ErrorCodes.DETALLE_ERROR);

				}
				/*}else {
				errors.add(ParameterNames.ID, ErrorCodes.MANDATORY_PARAMETER);
			}*/
			if(errors.hasErrors()) {
				target = ViewPaths.HOME;
				redirect = false;

			} else {
				if(contenidoDetalle!=null) {
					request.setAttribute(AttributeNames.CONTENIDO, contenidoDetalle);
					target = ViewPaths.VISTA_DETALLE;
					redirect = true;
				}
			}
		} else {
			// Mmm...
			logger.error("Action desconocida");
			// target ?
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
