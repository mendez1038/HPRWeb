package com.hpr.web.controller;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.dao.LineaPedidoDAO;
import com.david.training.dao.PedidoDAO;
import com.david.training.dao.impl.LineaPedidoDAOImpl;
import com.david.training.dao.impl.PedidoDAOImpl;
import com.david.training.exceptions.DataException;
import com.david.training.exceptions.ServiceException;
import com.david.training.model.Artista;
import com.david.training.model.Contenido;
import com.david.training.model.ContenidoCriteria;
import com.david.training.model.Usuario;
import com.david.training.service.ContenidoService;
import com.david.training.service.FavoritoService;
import com.david.training.service.PedidoService;
import com.david.training.service.Results;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.david.training.service.impl.FavoritoServiceImpl;
import com.david.training.service.impl.PedidoServiceImpl;
import com.hpr.web.config.ConfigurationManager;
import com.hpr.web.config.ConfigurationParameterNames;
import com.hpr.web.model.ErrorCodes;
import com.hpr.web.model.Errors;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;
import com.hpr.web.util.WebUtils;

@WebServlet("/contenido")
public class ContenidoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(ContenidoServlet.class.getName());
	private ContenidoService servicio = null;
	private FavoritoService servicioFavoritos = null;
	private PedidoService servicioPedido = null;
	private PedidoDAO pedidoDao = null;
	private LineaPedidoDAO lpDao = null;
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)) + 1;
	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT));
	public ContenidoServlet() {
		super();
		servicio = new ContenidoServiceImpl();
		servicioFavoritos = new FavoritoServiceImpl();
		pedidoDao = new PedidoDAOImpl();
		lpDao = new LineaPedidoDAOImpl();
		servicioPedido = new PedidoServiceImpl(pedidoDao, lpDao);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// servlet parameters
		String action = request.getParameter(ParameterNames.ACTION);
		String target = null;
		String idioma = null;
		int pageBusqueda = WebUtils.getPageNumber(request.getParameter(ParameterNames.PAGE_BUSQUEDA), 1);
		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}
		// Objetos
		Errors errors = new Errors();
		StringBuilder targetString = new StringBuilder();
		Contenido contenidoDetalle = null;
		ContenidoCriteria cc = null;
		Artista artista = null;
		// listas
		Results<Contenido> resultados = null;
		// parametros
		String titulo = null;
		String restriccionEdad = null;
		String tipoContenido = null;
		String artistaNombre = null;
		String idContenido = null;
		Integer id = null;
		if (Actions.BUSCAR.equalsIgnoreCase(action)) {
			titulo = request.getParameter(ParameterNames.TITULO);
			restriccionEdad = request.getParameter(ParameterNames.RESTRICCION_EDAD);
			tipoContenido = request.getParameter(ParameterNames.TIPO_CONTENIDO);
			artistaNombre = request.getParameter(ParameterNames.ARTISTA);
		    cc = new ContenidoCriteria();


		    boolean hayFiltros = false;

		    if (titulo != null && !titulo.trim().isEmpty()) {
		        cc.setTitulo(titulo.trim());
		        targetString.append("&" + ParameterNames.TITULO + "=" + cc.getTitulo());
		        hayFiltros = true;
		    }

		    if (restriccionEdad != null && !"null".equalsIgnoreCase(restriccionEdad)) {
		        cc.setRestriccionEdad(restriccionEdad);
		        targetString.append("&" + ParameterNames.RESTRICCION_EDAD + "=" + cc.getRestriccionEdad());
		        hayFiltros = true;
		    }

		    if (tipoContenido != null && !tipoContenido.trim().isEmpty()) {
		        cc.setIdTipoContenido(tipoContenido);
		        targetString.append("&" + ParameterNames.TIPO_CONTENIDO + "=" + cc.getIdTipoContenido());
		        hayFiltros = true;
		    }

		    if (artistaNombre != null && !artistaNombre.trim().isEmpty()) {
		        artista = new Artista();
		        artista.setNombreArtista(artistaNombre.trim());
		        cc.setArtista(artista.getNombreArtista());
		        targetString.append("&" + ParameterNames.ARTISTA + "=" + artista.getNombreArtista());
		        hayFiltros = true;
		    }

		    // Si no se ha metido NADA, ahora sí marcamos error
		    if (!hayFiltros) {
		        errors.add(ParameterNames.ACTION, ErrorCodes.SEARCH_ERROR);
		    }

			Locale locale = (Locale) SessionManager.get(request, WebConstants.USER_LOCALE);
			idioma = locale != null ? locale.getLanguage() : "es";
			// Opcional: definir orden por defecto
		    cc.setSortBy("FECHA_LANZAMIENTO");
		    cc.setDesc(true);
		    pageSize = 20;
			if (errors.hasErrors()) {
				target = ViewPaths.INDEX;
			} else {
				try {
					
					resultados = servicio.findByCriteria(cc,idioma, pageBusqueda, pageSize);
					request.setAttribute(ParameterNames.URL, targetString.toString());
					request.setAttribute(AttributeNames.RESULTADOS_BUSQUEDA, resultados.getPage());
					request.setAttribute(AttributeNames.TOTAL_BUSQUEDA, resultados.getTotal());

					int totalPagesBusqueda = (int) Math.ceil(resultados.getTotal() / (double) pageSize);
					int firstPagedPageBusqueda = Math.max(1, pageBusqueda - pagingPageCount);
					int lastPagedPageBusqueda = Math.min(totalPagesBusqueda, pageBusqueda + pagingPageCount);
					request.setAttribute("page", pageBusqueda); // Página actual
					request.setAttribute("totalPages", totalPagesBusqueda);
					request.setAttribute("firstPagedPage", firstPagedPageBusqueda);
					request.setAttribute("lastPagedPage", lastPagedPageBusqueda);
					target = ViewPaths.BUSQUEDA;
					logger.info(cc);
				} catch (DataException | ServiceException e) {
					logger.warn("Busqueda... ", e);
					errors.add(ParameterNames.ACTION, ErrorCodes.SEARCH_ERROR);
					target = ViewPaths.INDEX;
				}
			}
		} else if (Actions.BUSCAR_ID.equalsIgnoreCase(action)) {

			idioma = SessionManager.get(request, WebConstants.USER_LOCALE).toString();
			idContenido = request.getParameter(ParameterNames.ID);
			id = Integer.valueOf(idContenido);
			try {
				contenidoDetalle = servicio.findById(id, idioma);
			} catch (Exception e) {
				logger.warn("Detalle... " + id, e);
				errors.add(ParameterNames.ACTION, ErrorCodes.DETALLE_ERROR);

			}
			Usuario usuario = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			boolean esFavorito = false;
			if (usuario != null) {
				try {
					esFavorito = servicioFavoritos.esFavorito(usuario.getEmail(), contenidoDetalle.getIdContenido());
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			boolean comprado = false;
			if (usuario != null) {
				try {
					comprado = servicioPedido.comprado(usuario.getEmail(), contenidoDetalle.getIdContenido());
				} catch (DataException e) {
					/* log o ignora para no romper la vista */ }
			}
			// Añade ambos al request
			request.setAttribute(AttributeNames.CONTENIDO, contenidoDetalle);
			request.setAttribute(AttributeNames.ES_FAVORITO, Boolean.valueOf(esFavorito));
			request.setAttribute("comprado", comprado);
			if (errors.hasErrors()) {
				target = ViewPaths.HOME;
			} else {
				request.setAttribute(AttributeNames.CONTENIDO, contenidoDetalle);
				target = ViewPaths.VISTA_DETALLE;
			}
		} else {
			logger.error("Action desconocida");
			target = ViewPaths.HOME;
		}
		logger.info("➡️ Redirigiendo a vista: " + target);
		request.getRequestDispatcher(target).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
