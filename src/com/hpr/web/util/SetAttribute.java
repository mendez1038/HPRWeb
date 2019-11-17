package com.hpr.web.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.david.training.model.Usuario;
import com.hpr.web.controller.AttributeNames;
import com.hpr.web.model.Errors;


public class SetAttribute {
	
	public static final void setErrors(HttpServletRequest request, Errors errors) {
		request.setAttribute(AttributeNames.ERRORS, errors);
	}
	
	public static final void setUser(HttpServletRequest request, Usuario u) {
		request.setAttribute(AttributeNames.USUARIOS, u);
	}
	
	public static final <T> void setResults(HttpServletRequest request, List<T> results) {
		request.setAttribute(AttributeNames.RESULTADOS, results);
	}
	
	public static final void setResult(HttpServletRequest request, Object o) {
		request.setAttribute(AttributeNames.CONTENIDO, o);
	}
	
	public static final void setCatgories(HttpServletRequest request, Object o) {
		request.setAttribute(AttributeNames.CATEGORIAS, o);
	}
	
	public static final void setOthers(HttpServletRequest request, String s, Object o) {
		request.setAttribute(s, o);
	}
}
