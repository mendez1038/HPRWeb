package com.hpr.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ParameterUtils {
	
	public static final String print(Map<String, String[]> parametros) {
		
		StringBuilder sb = new StringBuilder();
		String[] values = null;
		for (String pname : parametros.keySet()) {
			sb.append(pname).append("= values{");
			values = parametros.get(pname);
			for (int i =0; i<values.length-1;i++) {
				sb.append(values[i]).append(", ");
			}
			sb.append(values[values.length-1]);
			sb.append("}");
			
		}
		return sb.toString();
	}
	
	public static final String getParameter(HttpServletRequest request, String name) {		
		String value = (String) request.getParameter(name);							
		if (value==null) value = "";
		return value;
	}
	
	public static String trimmer(String param) {
		return param.trim();
	}
	
	

}
