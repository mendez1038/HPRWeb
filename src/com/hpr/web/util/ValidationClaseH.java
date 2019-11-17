package com.hpr.web.util;

import javax.servlet.http.HttpServletRequest;

import com.hpr.web.controller.ParameterNames;
import com.mysql.cj.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationClaseH {
	public static final String getParameter(HttpServletRequest request, String name) {		
		String value = (String) request.getParameter(name);							
		if (value==null) {
			value = "";
		}else {
			value = value.trim();
		}
		return value;
	}
	
	public static final Integer parseInt(String s) {
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			Integer i = Integer.parseInt(s.trim());
			return i;
		}else {
		return null;
		}
	}
	
	public static final String isEmpty(String s) {
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			s=s.trim();
			return s;
		}else {
			return null;
		}
	}
	
	public static final Integer parseIntParameter(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			Integer i = Integer.parseInt(s.trim());
			return i;
		}else {
		return null;
		}
	}
	
	public static final String parameterIsEmpty(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			s=s.trim();
			return s;
		}else {
			return null;
		}
	}
	
	public static Date parameterDateFormat(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		DateFormat format = new SimpleDateFormat("yyyy");
		if(s!=null && !StringUtils.isEmptyOrWhitespaceOnly(s)) {
			Date date;
			try {
				date = format.parse(s);
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}

		}else {
			return null;
		}
	}
	
	public static final Date dateFormat(Date d) {
		
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = format.parse(format.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public static final Date dateFormat(String s) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public static final Double parseDoubleParameter(HttpServletRequest request, String name) {
		
		String s = getParameter(request, name);
		
		if(!StringUtils.isEmptyOrWhitespaceOnly(s)) {
			String[] split = s.split(",");
			if(split.length>1 && split.length<3) {
				s = split[0]+"."+split[1];
				Double i = Double.parseDouble(s.trim());
				return i;
			}else if(split.length==1) {
				Double i = Double.parseDouble(s.trim());
				return i;
			}else {
				return null;
			}
		}else {
		return null;
		}
	}
	
	public static String dateToString(Date date) {
		
		DateFormat format = new SimpleDateFormat("yyyy");
		String s = null;
		
		if(date!=null) {
			s = format.format(date);
		} else {
			s = "";
		}
		 
		 return s;
	}
	
	public static Boolean parseBooleanParameter(HttpServletRequest request, String name) {
		
		Boolean b = null;
		String s = getParameter(request, name);
		if(ParameterNames.TRUE.equalsIgnoreCase(s)) {
			b = true;
		}else {
			b = false;
		}
		return b;
		
	}
	
	public static String validateEmail(HttpServletRequest request, String name) {

		String s = parameterIsEmpty(request, name);
		if(s!=null) {
			String[] array = s.split("@");
			if(array.length>1) {
				String s1 = array[1];
				array = s1.split(".com");
				if(array.length!=0) {
						return s;
				} else {
					array = s1.split(".es");
					if(array.length!=0) {
						return s;
					}
				}
			}
		}
		return null;
	}
	
	public static String isNumber(HttpServletRequest request, String name) {
		
		Integer i = parseIntParameter(request, name);
		if(i!=null) {
			return i.toString();
		}
		return null;
	}
}
