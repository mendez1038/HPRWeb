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
import com.david.training.model.Favorito;
import com.david.training.model.Usuario;
import com.david.training.service.ContenidoService;
import com.david.training.service.FavoritoService;
import com.david.training.service.Results;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.david.training.service.impl.FavoritoServiceImpl;
import com.hpr.web.config.ConfigurationManager;
import com.hpr.web.config.ConfigurationParameterNames;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;
import com.hpr.web.util.WebUtils;

@WebServlet("/favoritos")
public class FavoritosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)) + 1;
	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT));

	private FavoritoService servicio = null;
	private ContenidoService servicioContenido = null;
	private static Logger logger = LogManager.getLogger(FavoritosServlet.class.getName());

	public FavoritosServlet() {
		super();
		servicio = new FavoritoServiceImpl();
		servicioContenido = new ContenidoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    // Anti-caché (opcional pero recomendable)
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

	    // Usuario en sesión
	    Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
	    if (u == null) {
	        request.getRequestDispatcher(ViewPaths.FAVORITOS).forward(request, response);
	        return;
	    }

	    // La JSP usa ?pageFavourites=...
	    int pagina = WebUtils.getPageNumber(request.getParameter("pageFavourites"), 1);
	    Results<Contenido> lista;

	    // Idioma seguro (minúsculas) con fallback
	    String idioma = "es";
	    Object locObj = SessionManager.get(request, WebConstants.USER_LOCALE);
	    if (locObj != null) {
	        String s = locObj.toString();
	        if (s.length() >= 2) idioma = s.substring(0, 2).toLowerCase();
	    }

	    try {
	        // OJO: si tu DAO es 0-based, usa (pagina - 1) * pageSize; sin +1
	        int start = (pagina - 1) * pageSize + 1;
	        lista = servicioContenido.favoritos(u.getEmail(), idioma, start, pageSize);

	        // La JSP espera estos nombres EXACTOS:
	        request.setAttribute(AttributeNames.RESULTADOS_FAVORITOS, lista.getPage()); // "resultados_favoritos"
	        request.setAttribute(AttributeNames.TOTAL_FAVORITOS, lista.getTotal());     // "total_favoritos"

	        int totalPages = (int) Math.ceil(lista.getTotal() / (double) pageSize);
	        int firstPagedPage = Math.max(1, pagina - pagingPageCount);
	        int lastPagedPage  = Math.min(totalPages, pagina + pagingPageCount);

	        // Estos también deben casar con la JSP:
	        request.setAttribute(ParameterNames.PAGE_FAVOURITES, pagina);
	        request.setAttribute(AttributeNames.TOTAL_PAGES_FAVOURITES, totalPages);         // "totalPagesFavourites"
	        request.setAttribute(AttributeNames.FIRST_PAGED_FAVOURITES, firstPagedPage);     // "firstPagedPageFavourites"
	        request.setAttribute(AttributeNames.LAST_PAGED_FAVOURITES, lastPagedPage);       // "lastPagedPageFavourites"

	    } catch (DataException e) {
	        throw new ServletException("Error al cargar favoritos", e);
	    }
	    
	    logger.info("[/favoritos] pageSize={} pagingPageCount={}", pageSize, pagingPageCount);
	    logger.info("[/favoritos] user={} idioma={} pagina={}", 
	                (u!=null?u.getEmail():"null"), idioma, pagina);
	    logger.info("[/favoritos] resultados.size={} total={}",
	                (lista!=null && lista.getPage()!=null ? lista.getPage().size() : null),
	                (lista!=null ? lista.getTotal() : null));
	    logger.info("[/favoritos] totalPages={} first={} last={}",
	                request.getAttribute(AttributeNames.TOTAL_PAGES_FAVOURITES),
	                request.getAttribute(AttributeNames.FIRST_PAGED_FAVOURITES),
	                request.getAttribute(AttributeNames.LAST_PAGED_FAVOURITES));


	    request.getRequestDispatcher(ViewPaths.FAVORITOS).forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter(ParameterNames.ACTION);
		Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
		if (u == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		String idParam = request.getParameter(ParameterNames.ID);
		if (idParam == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta id de contenido");
			return;
		}

		Integer idContenido;
		try {
			idContenido = Integer.valueOf(idParam);
		} catch (NumberFormatException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "id inválido");
			return;
		}

		Favorito favorito = new Favorito(u.getEmail(), idContenido, true);
		String target = request.getHeader("Referer");
		if (target == null || target.isEmpty()) {
			target = request.getContextPath() + "/favoritos"; // fallback
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Action {} - usuario: {} - contenido: {}", action, u.getEmail(), idContenido);
		}

		try {
			if (Actions.ANADIR_FAVORITO.equalsIgnoreCase(action)) {
				servicio.anadirFavorito(favorito);
			} else if (Actions.ELIMINAR_FAVORITO.equalsIgnoreCase(action)) {
				servicio.upadteEliminarFavorito(favorito); // (ojo con el typo "upadte")
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no soportada");
				return;
			}
		} catch (DataException e) {
			throw new ServletException("Error al actualizar favorito", e);
		}

		response.sendRedirect(target);
	}

}
