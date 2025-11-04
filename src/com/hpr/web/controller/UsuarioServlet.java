package com.hpr.web.controller;

import java.io.IOException;
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
			String email = ValidationUtils.emailValidator(request.getParameter(ParameterNames.EMAIL));
			// String contrasena =
			// ValidationUtils.passwordValidator(request.getParameter(ParameterNames.CONTRASENA));
			String contrasena = request.getParameter(ParameterNames.CONTRASENA);

			// Limpieza

			email = ParameterUtils.trimmer(email);

			// Validacion

			if (StringUtils.isNullOrEmpty(email)) {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
			}

			if (StringUtils.isNullOrEmpty(contrasena)) {
				errors.add(ParameterNames.CONTRASENA, ErrorCodes.MANDATORY_PARAMETER);
			}

			Usuario usuario = null;
			if (!errors.hasErrors()) {
				try {
					usuario = servicio.signIn(email, contrasena);
				} catch (DataException e) {
					logger.warn("autenticando " + email, e);
					errors.add(ParameterNames.ACTION, ErrorCodes.AUTHENTICATION_ERROR);
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
				CookieManager.addCookie(response, Actions.LOGIN, usuario.getEmail(), "/", 7 * 24 * 60 * 60);
				target = request.getContextPath();
				redirect = true;
			}
		} else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			SessionManager.set(request, SessionAttributeNames.USER, null);
			target = request.getContextPath() + ViewPaths.INDEX;
			redirect = true;
		} else if (Actions.PREREGISTRO.equalsIgnoreCase(action)) {
			target = ViewPaths.REGISTRO;
		} else if (Actions.REGISTRO.equalsIgnoreCase(action)) {
			// Recuperamos parametros

			String email = request.getParameter(ParameterNames.EMAIL);
			String contrasena = request.getParameter(ParameterNames.CONTRASENA);
			String nombre = request.getParameter(ParameterNames.NOMBRE);
			String apellidos = request.getParameter(ParameterNames.APELLIDOS);
			String genero = request.getParameter(ParameterNames.GENERO);
			String fechaNacimiento = request.getParameter(ParameterNames.FECHA_NACIMIENTO);
			Date fNacimiento = null;
			String telefono = request.getParameter(ParameterNames.TELEFONO);

			// validacion

			email = ValidationUtils.emailValidator(email);
			if (email == null) {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
			}
			contrasena = ValidationUtils.passwordValidator(contrasena);
			if (contrasena == null) {
				errors.add(ParameterNames.CONTRASENA, ErrorCodes.MANDATORY_PARAMETER);
			}
			nombre = ValidationUtils.stringOnlyLettersValidator(nombre, false);
			if (nombre == null) {
				errors.add(ParameterNames.NOMBRE, ErrorCodes.MANDATORY_PARAMETER);
			}
			apellidos = ValidationUtils.stringOnlyLettersValidator(apellidos, false);
			if (apellidos == null) {
				errors.add(ParameterNames.APELLIDOS, ErrorCodes.MANDATORY_PARAMETER);
			}

			if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
				fNacimiento = ValidationUtils.dateValidator(fechaNacimiento);
			} else {
				fNacimiento = null;
			}

			telefono = ValidationUtils.stringOnlyNumbersValidator(telefono, false);
			if (telefono == null) {
				errors.add(ParameterNames.TELEFONO, ErrorCodes.OPTIONAL_PARAMETER);
			}

			Usuario u = new Usuario();
			if (!errors.hasErrors()) {
				u.setEmail(email);
				u.setContrasena(contrasena);
				u.setNombre(nombre);
				u.setApellidos(apellidos);
				u.setGenero(genero);
				u.setFechaNacimiento(fNacimiento);
				u.setTelefono(telefono);
			}

			try {
				// Primero: si hay errores de validación, no seguimos
				if (errors.hasErrors()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Registro fallido: {}", errors);
					}
					request.setAttribute(AttributeNames.ERRORS, errors);
					target = ViewPaths.REGISTRO;
					request.getRequestDispatcher(target).forward(request, response);
					return; // 👈 muy importante: corta la ejecución
				}

				// Solo si está todo correcto, registramos al usuario
				servicio.signUp(u);

				if (logger.isDebugEnabled()) {
					logger.info("Usuario {} registrado.", u.getEmail());
				}

				SessionManager.set(request, SessionAttributeNames.USER, u);
				target = request.getContextPath() + ViewPaths.INDEX;
				redirect = true;

			} catch (MailException e) {
				errors.add(Actions.REGISTRO, ErrorCodes.MAIL_ERROR);
				logger.warn("Error al enviar correo de registro", e);
				request.setAttribute(AttributeNames.ERRORS, errors);
				target = ViewPaths.REGISTRO;
				request.getRequestDispatcher(target).forward(request, response);

			} catch (DataException e) {
				errors.add(Actions.REGISTRO, ErrorCodes.SIGNUP_ERROR);
				logger.warn("Error al registrar usuario", e);
				request.setAttribute(AttributeNames.ERRORS, errors);
				target = ViewPaths.REGISTRO;
				request.getRequestDispatcher(target).forward(request, response);
			}

		} else if (Actions.PREEDICION.equalsIgnoreCase(action)) {
			Usuario user = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			request.setAttribute(AttributeNames.USUARIOS, user);
			target = ViewPaths.PERFIL;

		} else if (Actions.EDICION.equalsIgnoreCase(action)) {
			Usuario user = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);

			String contrasena = ValidationUtils.passwordValidator(request.getParameter(ParameterNames.CONTRASENA));
			String nombre = ValidationUtils.stringOnlyLettersValidator(request.getParameter(ParameterNames.NOMBRE),
					false);
			String apellidos = ValidationUtils.apellidosValidator(request.getParameter(ParameterNames.APELLIDOS),
					false);
			String genero = ValidationUtils.stringOnlyLettersValidator(request.getParameter(ParameterNames.GENERO),
					false);
			String telefono = ValidationUtils.stringOnlyNumbersValidator(request.getParameter(ParameterNames.TELEFONO),
					false);

			Usuario update = new Usuario();

			// Mantener datos antiguos si el usuario deja el campo vacío
			update.setContrasena(contrasena != null ? contrasena : user.getContrasena());
			update.setNombre(nombre != null ? nombre : user.getNombre());
			update.setApellidos(apellidos != null ? apellidos : user.getApellidos());
			update.setGenero(genero != null ? genero : user.getGenero());
			update.setTelefono(telefono != null ? telefono : user.getTelefono());

			// El email no cambia
			update.setEmail(user.getEmail());

			try {
				servicio.update(update);
			} catch (InstanceNotFoundException e) {
				logger.info(e.getMessage(), e);
			} catch (DataException e) {
				logger.info(e.getMessage(), e);
			}
			target = ControllerPaths.USUARIO + "?" + ParameterNames.ACTION + "=" + Actions.PREEDICION;
			redirect = true;
		} else if (Actions.CAMBIAR_IDIOMA.equalsIgnoreCase(action)) {
			String localeName = request.getParameter(ParameterNames.LOCALE);
			// Recordar que hay que validar... lo que nos envian, incluso en algo como esto.
			// Buscamos entre los Locale soportados por la web:
			List<Locale> results = LocaleManager.getMatchedLocales(localeName);
			Locale newLocale = null;
			if (results.size() > 0) {
				newLocale = results.get(0);
			} else {
				logger.warn("Request locale not supported: " + localeName);
				newLocale = LocaleManager.getDefault();
			}

			SessionManager.set(request, WebConstants.USER_LOCALE, newLocale);
			CookieManager.addCookie(response, WebConstants.USER_LOCALE, newLocale.toString(), "/", 365 * 24 * 60 * 60);

			if (logger.isDebugEnabled()) {
				logger.debug("Locale changed to " + newLocale);
			}
			target = request.getHeader(ViewPaths.REFERER);
			redirect = true;

		} else if (Actions.ELIMINAR_CUENTA.equalsIgnoreCase(action)) {
			
			Usuario u = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
	        if (u == null) { response.sendRedirect(request.getContextPath()+ViewPaths.INDEX); return; }
	        target = ViewPaths.ELIMINAR_CUENTA;
			
		}else {
		
			logger.error("Action desconocida");
			target = request.getContextPath() + ViewPaths.INDEX;

		}
		if (redirect) {
			logger.info("Redirecting to " + target);
			response.sendRedirect(target);
		} else {
			logger.info("Forwarding to " + target);
			request.getRequestDispatcher(target).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			doGet(request, response);

        
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
