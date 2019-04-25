<%@ page import="com.hpr.web.model.*,com.hpr.web.controller.*, java.util.List" %>
<%
	Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new Errors();
%>