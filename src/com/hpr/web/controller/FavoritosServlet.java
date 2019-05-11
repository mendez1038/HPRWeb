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
import com.david.training.model.Favorito;
import com.david.training.model.Usuario;
import com.david.training.service.FavoritoService;
import com.david.training.service.impl.FavoritoServiceImpl;
import com.hpr.web.util.SessionManager;


@WebServlet("/favoritos")
public class FavoritosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   private FavoritoService servicio = null;
   private static Logger logger = LogManager.getLogger(FavoritosServlet.class.getName());
    public FavoritosServlet() {
        super();
        servicio = new FavoritoServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action= request.getParameter(ParameterNames.ACTION);
		String target = request.getHeader(ViewPaths.REFERER);
		Favorito favorito = null;
		Integer idContenido = Integer.valueOf(request.getParameter(ParameterNames.ID));
		Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}
		
		if(Actions.ANADIR_FAVORITO.equalsIgnoreCase(action)) {
			String email = u.getEmail();
			favorito.setEmail(email);
			favorito.setIdContenido(idContenido);
			
			try {
				servicio.añadirFavorito(favorito);
			} catch (DataException e) {
				
				e.printStackTrace();
			}
			
			
		
	}else if(Actions.ELIMINAR_FAVORITO.equalsIgnoreCase(action)) {
			String email = u.getEmail();
			favorito.setEmail(email);
			favorito.setIdContenido(idContenido);
			
			try {
				servicio.upadteEliminarFavorito(favorito);
			} catch (DataException e) {
				
				e.printStackTrace();
			}
		
	}	
	response.sendRedirect(target);
}



	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
