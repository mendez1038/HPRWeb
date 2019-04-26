package com.hpr.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.david.training.exceptions.DataException;
import com.david.training.exceptions.InstanceNotFoundException;
import com.david.training.model.Contenido;
import com.david.training.service.ContenidoService;
import com.david.training.service.impl.ContenidoServiceImpl;
import com.hpr.web.model.Carrito;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.WebConstants;





@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ContenidoService servicio = null;
    public CarritoServlet() {
        super();
       servicio = new ContenidoServiceImpl();
        }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action= request.getParameter(ParameterNames.ACTION);
		String target = request.getHeader(ViewPaths.REFERER);
		Integer idContenido = Integer.valueOf(request.getParameter(ParameterNames.ID));
		
		
		if(Actions.ANADIR.equalsIgnoreCase(action)) {
				try {
					addToCart(request, idContenido);
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
				} catch (DataException e) {
					
					e.printStackTrace();
				}
			
		}else if(Actions.ELIMINAR.equalsIgnoreCase(action)) {
			deleteCart(request, idContenido);
		}	
		response.sendRedirect(target);
	}

	protected void deleteCart(HttpServletRequest request, Integer idContenido) {
		HttpSession session = request.getSession();
		Carrito carrito = null;

		
		Object objCarrito = session.getAttribute(SessionAttributeNames.CARRITO);
		if (objCarrito != null) {
			carrito = (Carrito) objCarrito;
		} else {
			carrito = new Carrito();
		}
		carrito.deleteLineaCarrito(idContenido);
	}
	
	protected void addToCart(HttpServletRequest request, Integer idContenido) throws InstanceNotFoundException, DataException {
		HttpSession session = request.getSession();
		String idioma=SessionManager.get(request,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
		Carrito carrito = null;
		boolean checkDuplicated ;
		
		Contenido anadir = new Contenido();
		anadir = servicio.findPorId(idContenido, idioma);
	
		Object objCartBean = session.getAttribute(SessionAttributeNames.CARRITO);

		if (objCartBean != null) {
			carrito = (Carrito) objCartBean;
		} else {
			carrito = new Carrito();
			session.setAttribute(SessionAttributeNames.CARRITO, carrito);
		}
		checkDuplicated=carrito.addLineaCarrito(anadir);
		if (checkDuplicated) {
			request.setAttribute(ParameterNames.CONTENIDO_DUPLICADO,AttributeNames.YA_EN_CARRITO);
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
