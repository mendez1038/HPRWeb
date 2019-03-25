package com.hpr.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.exceptions.DataException;
import com.david.training.model.Usuario;
import com.david.training.service.UsuarioService;
import com.david.training.service.impl.UsuarioServiceImpl;
import com.hpr.web.model.ErrorCodes;
import com.hpr.web.model.Errors;
import com.hpr.web.util.ParameterUtils;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.ValidationUtils;
import com.mysql.cj.util.StringUtils;




@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioService servicio = null;
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class.getName());

	public UsuarioServlet() {
		servicio = new UsuarioServiceImpl();
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String action = request.getParameter(ParameterNames.ACTION);

		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}

		Errors errors = new Errors(); 
		String target = null;
		boolean redirect = false;

		if (Actions.LOGIN.equalsIgnoreCase(action)) {			
			// Recuperacion
			String email =  request.getParameter(ParameterNames.EMAIL);
			String contrasena = request.getParameter(ParameterNames.CONTRASENA);

			// Limpieza

			email = ParameterUtils.trimmer(email);
			contrasena = ParameterUtils.trimmer(contrasena);

			// Validacion 

			if (StringUtils.isNullOrEmpty(email)) {
				errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
			}

			if (StringUtils.isNullOrEmpty(contrasena)) {
				errors.add(ParameterNames.CONTRASENA,ErrorCodes.MANDATORY_PARAMETER);
			}

			Usuario usuario = null;
			if (!errors.hasErrors()) {
				try {
					usuario = servicio.signIn(email, contrasena);
				} catch (DataException e) {
					logger.warn("autenticando "+email, e);
					errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);
				}
			}
			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}				
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.LOGIN;				
			} else {			
				if (logger.isDebugEnabled()) {
					logger.info("Usuario {} autenticado.");
				}				
				SessionManager.set(request, SessionAttributeNames.USER, usuario);						
				target = request.getContextPath()+ViewPaths.HOME;					
				redirect = true;
			}
		} else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			SessionManager.set(request, SessionAttributeNames.USER, null);
			target = ViewPaths.INDEX;
			redirect = true;
		}else if (Actions.REGISTRO.equalsIgnoreCase(action)) {
			//Recuperamos parametros
			
			String email =  request.getParameter(ParameterNames.EMAIL);
			String contrasena = request.getParameter(ParameterNames.CONTRASENA);
			String nombre =  request.getParameter(ParameterNames.NOMBRE);
			String apellidos = request.getParameter(ParameterNames.APELLIDOS);
			String genero =  request.getParameter(ParameterNames.GENERO);
			String fechaNacimiento = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			String telefono =  request.getParameter(ParameterNames.TELEFONO);
			Date fNacimiento = null;
			//validacion

			email = ValidationUtils.emailValidator(email);
			if(email == null) {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
			}
			contrasena = ValidationUtils.passwordValidator(contrasena);
			if(contrasena == null) {
				errors.add(ParameterNames.CONTRASENA, ErrorCodes.MANDATORY_PARAMETER);
			}
			nombre = ValidationUtils.stringOnlyLettersValidator(nombre, true);
			if(nombre == null) {
				errors.add(ParameterNames.NOMBRE, ErrorCodes.MANDATORY_PARAMETER);
			}
			apellidos = ValidationUtils.stringOnlyLettersValidator(apellidos, false);
			if(apellidos == null) {
				errors.add(ParameterNames.APELLIDOS, ErrorCodes.MANDATORY_PARAMETER);
			}
			genero = ValidationUtils.stringOnlyLettersValidator(genero, false);
			if(genero == null) {
				errors.add(ParameterNames.GENERO, ErrorCodes.MANDATORY_PARAMETER);
			}


			if (!StringUtils.isEmptyOrWhitespaceOnly(fechaNacimiento)) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				try {
					fNacimiento = df.parse(fechaNacimiento);
				} catch (ParseException e) {
					logger.warn("parse exception "+ e);
					errors.add(ParameterNames.CONTRASENA,ErrorCodes.MANDATORY_PARAMETER);
				}

			}

			if(fechaNacimiento == null) {
				errors.add(ParameterNames.FECHA_NACIMIENTO, ErrorCodes.MANDATORY_PARAMETER);
			}
			telefono = ValidationUtils.stringOnlyLettersValidator(telefono, false);
			if(telefono == null) {
				errors.add(ParameterNames.TELEFONO, ErrorCodes.MANDATORY_PARAMETER);
			}

			Usuario u = new Usuario();
			if(!errors.hasErrors()) {
				u.setEmail(email);
				u.setContrasena(contrasena);
				u.setNombre(nombre);
				u.setApellidos(apellidos);
				u.setGenero(genero);
				u.setFechaNacimiento(fNacimiento);
				u.setTelefono(telefono);
			}
				if (errors.hasErrors()) {	
					if (logger.isDebugEnabled()) {
						logger.debug("Registro fallido: {}", errors);
					}				
					request.setAttribute(AttributeNames.ERRORS, errors);				
					target = ViewPaths.REGISTRO;	

				} else {
					if (logger.isDebugEnabled()) {
						logger.info("Usuario "+u.getEmail()+" registrado.");
					}				
					request.setAttribute(AttributeNames.USUARIOS, u);						
					target = ViewPaths.HOME;	
				}
		
		}else {
			logger.error("Action desconocida");
			target = ViewPaths.INDEX;
		}
		if (redirect) {
			logger.info("Redirecting to "+target);
			response.sendRedirect(target);
		} else {
			logger.info("Forwarding to "+target);
			request.getRequestDispatcher(target).forward(request, response);
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}