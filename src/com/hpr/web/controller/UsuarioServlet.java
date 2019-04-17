package com.hpr.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.david.training.exceptions.DataException;
import com.david.training.exceptions.InstanceNotFoundException;
import com.david.training.exceptions.MailException;
import com.david.training.model.Usuario;
import com.david.training.service.UsuarioService;
import com.david.training.service.impl.UsuarioServiceImpl;
import com.hpr.web.model.ErrorCodes;
import com.hpr.web.model.Errors;
import com.hpr.web.util.CookieManager;
import com.hpr.web.util.DateUtils;
import com.hpr.web.util.LocaleManager;
import com.hpr.web.util.ParameterUtils;
import com.hpr.web.util.SessionManager;
import com.hpr.web.util.ValidationUtils;
import com.hpr.web.util.WebConstants;
import com.mysql.cj.util.StringUtils;




@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioService servicio = null;
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class.getName());

	public UsuarioServlet() {
		servicio = new UsuarioServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

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
					logger.info("Usuario {} autenticado.", usuario.getEmail());
				}				
				SessionManager.set(request, SessionAttributeNames.USER, usuario);
				CookieManager.addCookie(response, Actions.LOGIN, usuario.getEmail(), "/", 7*24*60*60);
				target = request.getContextPath();					
				redirect = true;
			}
		} else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			SessionManager.set(request, SessionAttributeNames.USER, null);
			target = request.getContextPath()+ViewPaths.INDEX;
			redirect = true;
		}else if (Actions.PREREGISTRO.equalsIgnoreCase(action)) {
			target= ViewPaths.REGISTRO;
		}else if(Actions.REGISTRO.equalsIgnoreCase(action)) {
			target = null;
    		redirect = false;
			
			//Recuperamos parametros

			String email =  request.getParameter(ParameterNames.EMAIL);
			String contrasena = request.getParameter(ParameterNames.CONTRASENA);
			String nombre =  request.getParameter(ParameterNames.NOMBRE);
			String apellidos = request.getParameter(ParameterNames.APELLIDOS);
			String genero =  request.getParameter(ParameterNames.GENERO);
			String fechaNacimiento = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			String telefono =  request.getParameter(ParameterNames.TELEFONO);
			SimpleDateFormat fecha = (SimpleDateFormat) DateUtils.SHORT_FORMAT_DATE;
			Date fNacimiento = null;
			try {
				fNacimiento = fecha.parse(fechaNacimiento);
			} catch (ParseException e1) {
				logger.warn("Fecha en un formato incorrecto  " +e1);
				errors.add(ParameterNames.ACTION,ErrorCodes.PARSE_ERROR);
			}
			//validacion
			email = ValidationUtils.emailValidator(email);
			if(email == null) {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
			} 
			contrasena = ValidationUtils.passwordValidator(contrasena);
			if(contrasena == null) {
				errors.add(ParameterNames.CONTRASENA, ErrorCodes.MANDATORY_PARAMETER);
			} 
			nombre = ValidationUtils.stringOnlyLettersValidator(nombre, false);
			if(nombre == null) {
				errors.add(ParameterNames.NOMBRE, ErrorCodes.OPTIONAL_PARAMETER);
			}
			apellidos = ValidationUtils.apellidosValidator(apellidos, false);
			if(apellidos == null) {
				errors.add(ParameterNames.APELLIDOS, ErrorCodes.OPTIONAL_PARAMETER);
			}
			
			telefono = ValidationUtils.stringOnlyNumbersValidator(telefono, false);
			if(telefono == null) {
				errors.add(ParameterNames.TELEFONO, ErrorCodes.OPTIONAL_PARAMETER);
			}

			Usuario u = new Usuario();
			
				u.setEmail(email);
				u.setContrasena(contrasena);
				u.setNombre(nombre);
				u.setApellidos(apellidos);
				u.setGenero(genero);
				u.setFechaNacimiento(fNacimiento);
				u.setTelefono(telefono);
			

			u = (Usuario) SessionManager.get(request, AttributeNames.USUARIOS);

			
				try {	
					 servicio.signUp(u);
					if (u == null) {
						
						if (logger.isDebugEnabled()) {
							logger.debug("Registro fallido: {}", errors);
						}
						//errors.add(ParameterNames.ACTION,ErrorCodes.SIGNUP_ERROR);
						request.setAttribute(AttributeNames.ERRORS, AttributeNames.DUPLICATED_USER);
						target = ViewPaths.REGISTRO;
					} else {
						if (logger.isDebugEnabled()) {
							logger.info("Usuario {} registrado.", u.getEmail());
						}				
						SessionManager.set(request, SessionAttributeNames.USER, u);						
						target = ViewPaths.HOME;
						redirect = true;
					}
				} catch (MailException e) {
					errors.add(Actions.REGISTRO, ErrorCodes.MAIL_ERROR);
				} catch (DataException e) {
					errors.add(Actions.REGISTRO, ErrorCodes.SIGNUP_ERROR);
				}

		}else if(Actions.PREEDICION.equalsIgnoreCase(action)) {
			Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			request.setAttribute(AttributeNames.USUARIOS, user);
			target = ViewPaths.PERFIL;
		
		}else if(Actions.EDICION.equalsIgnoreCase(action)) {
			Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			String contrasena=request.getParameter(ParameterNames.CONTRASENA);
			String nombre=request.getParameter(ParameterNames.NOMBRE);
			String apellidos=request.getParameter(ParameterNames.APELLIDOS);
			String genero=request.getParameter(ParameterNames.GENERO);
			String fechaNacimiento = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			String telefono=request.getParameter(ParameterNames.TELEFONO);	
			SimpleDateFormat fecha = (SimpleDateFormat) DateUtils.SHORT_FORMAT_DATE;
			Date fNacimiento = null;
			try {
				fNacimiento = fecha.parse(fechaNacimiento);
			} catch (ParseException e1) {
				logger.warn("Fecha  en un formato incorrecto " +e1);
				errors.add(ParameterNames.ACTION,ErrorCodes.PARSE_ERROR);
			}
			contrasena = ValidationUtils.passwordValidator(contrasena);
			if(contrasena == null) {
				errors.add(ParameterNames.CONTRASENA, ErrorCodes.OPTIONAL_PARAMETER);
			} 
			nombre = ValidationUtils.stringOnlyLettersValidator(nombre, false);
			if(nombre == null) {
				errors.add(ParameterNames.NOMBRE, ErrorCodes.OPTIONAL_PARAMETER);
			}
			apellidos = ValidationUtils.apellidosValidator(apellidos, false);
			if(apellidos == null) {
				errors.add(ParameterNames.APELLIDOS, ErrorCodes.OPTIONAL_PARAMETER);
			}
			genero = ValidationUtils.stringOnlyLettersValidator(genero, false);
			if(genero == null) {
				errors.add(ParameterNames.GENERO, ErrorCodes.OPTIONAL_PARAMETER);
			}

			telefono = ValidationUtils.stringOnlyNumbersValidator(telefono, false);
			if(telefono == null) {
				errors.add(ParameterNames.TELEFONO, ErrorCodes.OPTIONAL_PARAMETER);
			}
			Usuario update=new Usuario();
			update.setContrasena(contrasena);
			update.setNombre(nombre);
			update.setApellidos(apellidos);
			update.setGenero(genero);
			update.setFechaNacimiento(fNacimiento);
			update.setTelefono(telefono);
			update.setEmail(user.getEmail());
			
			try {
				servicio.update(update);
			} catch (InstanceNotFoundException e) {
				logger.info(e.getMessage(),e);
			} catch (DataException e) {
				logger.info(e.getMessage(),e);
			}
			target = ControllerPaths.USUARIO+"?"+ParameterNames.ACTION+"="+Actions.PREEDICION;
			redirect=true;
		}else if (Actions.CAMBIAR_IDIOMA.equalsIgnoreCase(action)) {
			String localeName = request.getParameter(ParameterNames.LOCALE);
			// Recordar que hay que validar... lo que nos envian, incluso en algo como esto.
			// Buscamos entre los Locale soportados por la web:
			List<Locale> results = LocaleManager.getMatchedLocales(localeName);
			Locale newLocale = null;
			if (results.size()>0) {
				newLocale = results.get(0);
			} else {
				logger.warn("Request locale not supported: "+localeName);
				newLocale = LocaleManager.getDefault();
			}

			SessionManager.set(request, WebConstants.USER_LOCALE, newLocale);			
			CookieManager.addCookie(response, WebConstants.USER_LOCALE, newLocale.toString(), "/", 365*24*60*60);

			if (logger.isDebugEnabled()) {
				logger.debug("Locale changed to "+newLocale);
			}
			target=request.getHeader(ViewPaths.REFERER);
			redirect=true;
			
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
