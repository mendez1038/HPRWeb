package com.hpr.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.exceptions.DataException;
import com.david.training.model.Categoria;
import com.david.training.model.Contenido;
import com.david.training.model.Pais;
import com.david.training.model.TipoContenido;
import com.david.training.service.CategoriaService;
import com.david.training.service.ContenidoService;
import com.david.training.service.PaisService;
import com.david.training.service.Results;
import com.david.training.service.TipoContenidoService;
import com.david.training.service.impl.CategoriaServiceImpl;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.david.training.service.impl.PaisServiceImpl;
import com.david.training.service.impl.TipoContenidoServiceImpl;
import com.hpr.web.config.ConfigurationManager;
import com.hpr.web.config.ConfigurationParameterNames;
import com.hpr.web.controller.AttributeNames;
import com.hpr.web.controller.ParameterNames;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;
import com.hpr.web.util.WebUtils;


public class FilterSearchFilter implements Filter {

	private PaisService paisService = null;
	private CategoriaService categoriaService = null;
	private TipoContenidoService tipoService = null;

	private ContenidoService contenidoService = null;


	private Logger logger=LogManager.getLogger(FilterSearchFilter.class);
	
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT))+1; 
	
	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 

	public FilterSearchFilter() {
		paisService = new PaisServiceImpl();
		categoriaService = new CategoriaServiceImpl();
		tipoService = new TipoContenidoServiceImpl();

		contenidoService = new ContenidoServiceImpl();

	}


	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		HttpServletRequest httpRequest= (HttpServletRequest) request;
		HttpServletResponse httpResponse= (HttpServletResponse) response;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		
		int page = WebUtils.
				getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
		Results<Contenido> rebajas;
		String idiomaPagina=SessionManager.get(httpRequest,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
		try {

			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = httpRequest.getHeader(headerName);
				if(logger.isDebugEnabled()) {
					logger.debug(headerName+"="+headerValue);
				}
			}	
			

			rebajas = contenidoService.findAllByRebajas(idiomaPagina, (page-1)*pageSize+1, pageSize);
			request.setAttribute(AttributeNames.RESULTADOS_REBAJAS, rebajas.getPage());
			request.setAttribute(AttributeNames.TOTAL_REBAJAS, rebajas.getTotal());

			int totalPagesSales = (int) Math.ceil(rebajas.getTotal()/(double)pageSize);
			int firstPagedPageSales = Math.max(1, page-pagingPageCount);
			int lastPagedPageSales = Math.min(totalPagesSales, page+pagingPageCount);
			request.setAttribute(AttributeNames.TOTAL_PAGES_SALES, totalPagesSales);
			request.setAttribute(AttributeNames.FIRST_PAGED_PAGES_SALES, firstPagedPageSales);
			request.setAttribute(AttributeNames.LAST_PAGED_PAGES_SALES, lastPagedPageSales);
			
			
			List<Contenido> ventas = contenidoService.findAllByVentas(idiomaPagina);


			List<Pais> paises = paisService.findAll(idiomaPagina);
			List<Categoria> categorias = categoriaService.findAll(idiomaPagina);
			List<TipoContenido> tipos = tipoService.findAll(idiomaPagina);

			request.setAttribute(AttributeNames.RESULTADOS_REBAJAS, rebajas);
			request.setAttribute(AttributeNames.RESULTADOS_VENTAS, ventas);

			request.setAttribute(AttributeNames.PAISES, paises);
			request.setAttribute(AttributeNames.TIPOS, tipos);
			request.setAttribute(AttributeNames.CATEGORIAS, categorias);


		} catch (DataException e) {
			logger.fatal(e.getMessage(),e);
			System.exit(1);
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
