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
import com.david.training.model.TipoContenido;
import com.david.training.service.TipoContenidoService;
import com.david.training.service.impl.TipoContenidoServiceImpl;
import com.hpr.web.controller.AttributeNames;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;



public class FilterSearchFilter implements Filter {

	private TipoContenidoService tipoService = null;

	private Logger logger=LogManager.getLogger(FilterSearchFilter.class);
	
	public FilterSearchFilter() {
		tipoService = new TipoContenidoServiceImpl();
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		HttpServletRequest httpRequest= (HttpServletRequest) request;
		HttpServletResponse httpResponse= (HttpServletResponse) response;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		
		String idiomaPagina=SessionManager.get(httpRequest,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
		
		try {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = httpRequest.getHeader(headerName);
				if(logger.isDebugEnabled()) {
					logger.debug(headerName+"="+headerValue);
				}
			}	
			
			List<TipoContenido> tipos = tipoService.findAll(idiomaPagina);
			request.setAttribute(AttributeNames.TIPOS, tipos);

		} catch (DataException e) {
			logger.fatal(e.getMessage(),e);
			System.exit(1);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}