package com.hpr.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.david.training.exceptions.DataException;
import com.david.training.exceptions.InstanceNotFoundException;
import com.david.training.model.Usuario;
import com.david.training.service.UsuarioService;
import com.david.training.service.impl.UsuarioServiceImpl;
import com.hpr.web.util.SessionManager;

/**
 * Servlet implementation class EliminarCuenta
 */
@WebServlet("/eliminarcuenta")
public class EliminarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioService servicio = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarCuenta() {
    	servicio = new UsuarioServiceImpl();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
	    if (u == null) { 
	        response.sendRedirect(request.getContextPath() + ViewPaths.LOGIN); 
	        return; 
	    }

	    // Leer parámetros del formulario
	    String password = request.getParameter(ParameterNames.CONTRASENA);
	    String confirm  = request.getParameter("confirm"); // el input del JSP se debe llamar "confirm"

	    // Validación mínima
	    if (password == null || password.isEmpty() || confirm == null || !confirm.trim().equalsIgnoreCase("ELIMINAR")) {
	        request.setAttribute("error", "Por favor confirma la eliminación escribiendo ELIMINAR y tu contraseña.");
	        request.getRequestDispatcher(ViewPaths.ELIMINAR_CUENTA).forward(request, response);
	        return;
	    }

	    try {
	        // Ejecutar borrado (el servicio valida password internamente con PasswordEncryptionUtil)
	        servicio.eliminarCuenta(u.getEmail(), password);

	        // Cerrar sesión
	        request.getSession().invalidate();

	        // Redirigir a home
	        response.sendRedirect(request.getContextPath() + ViewPaths.INDEX);
	        return;

	    } catch (InstanceNotFoundException e) {
	        request.setAttribute("error", "La cuenta no existe.");
	        request.getRequestDispatcher(ViewPaths.ELIMINAR_CUENTA).forward(request, response);
	    } catch (DataException e) {
	        request.setAttribute("error", "No se pudo eliminar la cuenta. Inténtalo más tarde.");
	        request.getRequestDispatcher(ViewPaths.ELIMINAR_CUENTA).forward(request, response);
	    }
	}

}
