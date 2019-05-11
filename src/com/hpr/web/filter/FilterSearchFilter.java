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


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.exceptions.DataException;
import com.david.training.model.Categoria;
import com.david.training.model.Pais;
import com.david.training.model.TipoContenido;
import com.david.training.service.CategoriaService;
import com.david.training.service.PaisService;
import com.david.training.service.TipoContenidoService;
import com.david.training.service.impl.CategoriaServiceImpl;
import com.david.training.service.impl.PaisServiceImpl;
import com.david.training.service.impl.TipoContenidoServiceImpl;
import com.hpr.web.controller.AttributeNames;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;


public class FilterSearchFilter implements Filter {

    private PaisService paisService = null;
    private CategoriaService categoriaService = null;
    private TipoContenidoService tipoService = null;
    
   // private ContenidoService contenidoService = null;
  
    
    private Logger logger=LogManager.getLogger(FilterSearchFilter.class);
    
    public FilterSearchFilter() {
        paisService = new PaisServiceImpl();
        categoriaService = new CategoriaServiceImpl();
        tipoService = new TipoContenidoServiceImpl();
        
        //contenidoService = new ContenidoServiceImpl();
        
    }

	
	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest= (HttpServletRequest) request;
			//HttpServletResponse httpResponse= (HttpServletResponse) response;
	
			Enumeration<String> headerNames = httpRequest.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = httpRequest.getHeader(headerName);
				if(logger.isDebugEnabled()) {
					logger.debug(headerName+"="+headerValue);
				}
			}	
			String idiomaPagina=SessionManager.get(httpRequest,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
			
//			List<Contenido> rebajas =  contenidoService.findAllByRebajas(idiomaPagina);
//			List<Contenido> ventas = contenidoService.findAllByVentas(idiomaPagina);
//			Results<Contenido> favoritos = contenidoService.favoritos(email, idiomaPagina, startIndex, count);
//			
			List<Pais> paises = paisService.findAll(idiomaPagina);
			List<Categoria> categorias = categoriaService.findAll(idiomaPagina);
			List<TipoContenido> tipos = tipoService.findAll(idiomaPagina);
			
//			request.setAttribute(AttributeNames.RESULTADOS_REBAJAS, rebajas);
//			request.setAttribute(AttributeNames.RESULTADOS_VENTAS, ventas);
//			request.setAttribute(AttributeNames.FAVORITOS, favoritos);
			
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
