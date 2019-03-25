<%@ page import="java.util.List" %>
<%@ page import="com.hpr.web.controller.*, com.hpr.web.model.*" %>
<%
	List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
	for (String error: parameterErrors) {
			%><li><%=error%></li>
	<%
	}
%>