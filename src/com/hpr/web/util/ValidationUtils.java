package com.hpr.web.util;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.util.StringUtils;

public class ValidationUtils {
	private static Logger logger = LogManager.getLogger(ValidationUtils.class.getName());

	public static final String getParameter(HttpServletRequest request, String name) {		
		String value = (String) request.getParameter(name);							
		if (value==null) {
			value = "";
		}else {
			value = value.trim();
		}
		return value;
	}
	
	public static Integer intValidator(String parameter) {
			if(!StringUtils.isEmptyOrWhitespaceOnly(parameter)) {
				return Integer.parseInt(parameter.trim());
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

	public static Date dateValidator (String parameter) {
		try {
			if(!StringUtils.isEmptyOrWhitespaceOnly(parameter)) {
				return DateUtils.SHORT_FORMAT_DATE.parse(ParameterUtils.trimmer(parameter));
			}else {
				return null;
			}
		}catch (ParseException ex) {
			logger.warn(ex.getMessage(), ex);
			return null;
		}
	}

	public static Double doubleValidator (String parameter) {
		try {
			if(!StringUtils.isEmptyOrWhitespaceOnly(parameter)) {
				return Double.parseDouble(ParameterUtils.trimmer(parameter));
			}else {
				return null;
			}
		}catch (NumberFormatException ex) {
			logger.warn(ex.getMessage(), ex);
			return null;
		}
	}

	public static String passwordValidator (String psswd) {

		psswd = ParameterUtils.trimmer(psswd);


		if(StringUtils.isEmptyOrWhitespaceOnly(psswd)) {
			return null;
		}

		else {
			return psswd;
		}

	}

	public static String emailValidator (String email) {
		email = ParameterUtils.trimmer(email);

		if(StringUtils.isEmptyOrWhitespaceOnly(email)) {
			return null;
		}

		if(emailIsValid(email)) {
			return email;
		}else {
			return null;
		}
	}

	public static String apellidosValidator (String parameter, boolean isName){


		if(!isName) {
			if(!stringOnlyLetters(parameter)) {
				parameter = null;
			}
		}else {
			if(!stringWithoutNumber(parameter)) {
				parameter =  null;
			}
		}
		return parameter;

	}

	public static String stringOnlyLettersValidator (String parameter, boolean isName){

		parameter = ParameterUtils.trimmer(parameter);

		if(!isName) {
			if(!stringOnlyLetters(parameter)) {
				parameter = null;
			}
		}else {
			if(!stringWithoutNumber(parameter)) {
				parameter =  null;
			}
		}
		return parameter;

	}

	public static String stringOnlyNumbersValidator (String parameter, boolean isName){

		parameter = ParameterUtils.trimmer(parameter);

		if(!isName) {
			if(!stringOnlyNumbers(parameter)) {
				parameter = null;
			}
		}else {
			if(stringWithoutNumber(parameter)) {
				parameter =  null;
			}
		}
		return parameter;

	}



	private static boolean emailIsValid (String email) {

		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	@SuppressWarnings("unused")
	private static boolean psswdIsCorrect (String psswd) {

		String psswdPattern = "^(?=.{8,255}$)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";
		Pattern p = Pattern.compile(psswdPattern);
		Matcher m = p.matcher(psswd);
		return m.matches();
	}

	private static boolean stringOnlyLetters (String arg) {

		String argPattern = "[a-zA-Z]+";
		Pattern p = Pattern.compile(argPattern);
		Matcher m = p.matcher(arg);
		return m.matches();

	}

	private static boolean stringWithoutNumber (String arg) {
		String argPattern = "^\\p{L}+(?: \\p{L}+)*$";
		Pattern p = Pattern.compile(argPattern);
		Matcher m = p.matcher(arg);
		return m.matches();
	}

	private static boolean stringOnlyNumbers (String arg) {

		String argPattern = "[0-9]+";
		Pattern p = Pattern.compile(argPattern);
		Matcher m = p.matcher(arg);
		return m.matches();

	}

}
