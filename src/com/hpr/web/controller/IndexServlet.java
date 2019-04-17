package com.hpr.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.exceptions.DataException;
import com.david.training.model.Contenido;
import com.david.training.service.ContenidoService;
import com.david.training.service.Results;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.hpr.web.config.ConfigurationManager;
import com.hpr.web.config.ConfigurationParameterNames;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;
import com.hpr.web.util.WebUtils;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT))+2; 
	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 
	private ContenidoService servicio = null;
	private static Logger logger = LogManager.getLogger(ContenidoServlet.class);
    public IndexServlet() {
        super();
        servicio = new ContenidoServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = null;
		String idiomaPagina=SessionManager.get(request,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
		int page = WebUtils.
				getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
		
		Results<Contenido> all;
		Results<Contenido> rebajas;
		Results<Contenido> novedades;
		try {
			all = servicio.findAllByVentas(idiomaPagina, (page-1)*pageSize+1, pageSize);
			rebajas = servicio.findAllByRebajas(idiomaPagina, page, pageSize);
			novedades = servicio.findAllByDate(idiomaPagina, page, pageSize);
			request.setAttribute(AttributeNames.RESULTADOS_VENTAS, all.getPage());
			request.setAttribute(AttributeNames.RESULTADOS_REBAJAS, rebajas.getPage());
			request.setAttribute(AttributeNames.RESULTADOS_NOVEDADES, novedades.getPage());
			request.setAttribute(AttributeNames.TOTAL, all.getTotal());
			
			int totalPages = (int) Math.ceil(all.getTotal()/(double)pageSize);
			int firstPagedPage = Math.max(1, page-pagingPageCount);
			int lastPagedPage = Math.min(totalPages, page+pagingPageCount);
			request.setAttribute(ParameterNames.PAGE, page);
			request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
			request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
			request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
		} catch (DataException e) {
			logger.debug(e);
		}
		target= ViewPaths.HOME;
		request.getRequestDispatcher(target).forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
