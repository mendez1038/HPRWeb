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
			ConfigurationManager.getInstance().getParameter(ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)) + 1;
	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT));

	private static Logger logger = LogManager.getLogger(ContenidoServlet.class);

	private ContenidoService servicio = null;

	public IndexServlet() {
		super();
		servicio = new ContenidoServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = null;
		String idiomaPagina = SessionManager.get(request, WebConstants.USER_LOCALE).toString().substring(0, 2)
				.toUpperCase();
		int pageNovedades = WebUtils.getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
		int pageVendidos = WebUtils.getPageNumber(request.getParameter(ParameterNames.PAGE_SALES), 1);
		int pageDescuentos = WebUtils.getPageNumber(request.getParameter(ParameterNames.PAGE_DISCOUNT), 1);

		Results<Contenido> novedades;
		Results<Contenido> masVendidos;
		Results<Contenido> mayoresDescuentos;

		try {

			novedades = servicio.findAllByDate(idiomaPagina, (pageNovedades - 1) * pageSize + 1, pageSize);
			request.setAttribute(AttributeNames.RESULTADOS_NOVEDADES, novedades.getPage());
			request.setAttribute(AttributeNames.TOTAL, novedades.getTotal());

			int totalPages = (int) Math.ceil(novedades.getTotal() / (double) pageSize);
			int firstPagedPage = Math.max(1, pageNovedades - pagingPageCount);
			int lastPagedPage = Math.min(totalPages, pageNovedades + pagingPageCount);
			request.setAttribute(ParameterNames.PAGE, pageNovedades);
			request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
			request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
			request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);

			masVendidos = servicio.findAllByVentas(idiomaPagina, (pageVendidos - 1) * pageSize + 1, pageSize);
			request.setAttribute(AttributeNames.RESULTADOS_VENTAS, masVendidos.getPage());
			request.setAttribute(AttributeNames.TOTAL_SALES, masVendidos.getTotal());

			int totalPagesVendidos = (int) Math.ceil(masVendidos.getTotal() / (double) pageSize);
			int firstPagedPageVendidos = Math.max(1, pageVendidos - pagingPageCount);
			int lastPagedPageVendidos = Math.min(totalPagesVendidos, pageVendidos + pagingPageCount);
			request.setAttribute(ParameterNames.PAGE_SALES, pageVendidos);
			request.setAttribute(AttributeNames.TOTAL_PAGES_SALES, totalPagesVendidos);
			request.setAttribute(AttributeNames.FIRST_PAGED_PAGES_SALES, firstPagedPageVendidos);
			request.setAttribute(AttributeNames.LAST_PAGED_PAGES_SALES, lastPagedPageVendidos);

			mayoresDescuentos = servicio.findAllByRebajas(idiomaPagina, (pageDescuentos - 1) * pageSize + 1, pageSize);
			request.setAttribute(AttributeNames.RESULTADOS_REBAJAS, mayoresDescuentos.getPage());
			request.setAttribute(AttributeNames.TOTAL_REBAJAS, mayoresDescuentos.getTotal());

			int totalPagesDescuentos = (int) Math.ceil(mayoresDescuentos.getTotal() / (double) pageSize);
			int firstPagedPageDescuentos = Math.max(1, pageDescuentos - pagingPageCount);
			int lastPagedPageDescuentos = Math.min(totalPagesDescuentos, pageDescuentos + pagingPageCount);
			request.setAttribute(ParameterNames.PAGE_DISCOUNT, pageDescuentos);
			request.setAttribute(AttributeNames.TOTAL_PAGES_DISCOUNT, totalPagesDescuentos);
			request.setAttribute(AttributeNames.FIRST_PAGED_DISCOUNT, firstPagedPageDescuentos);
			request.setAttribute(AttributeNames.LAST_PAGED_DISCOUNT, lastPagedPageDescuentos);

		} catch (DataException e) {
			logger.debug(e);
		}
		target = ViewPaths.HOME;
		request.getRequestDispatcher(target).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
