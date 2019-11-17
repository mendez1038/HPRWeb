<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.hpr.web.controller.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope['user-locale']}" scope="session" />
<fmt:setBundle basename="resources.Messages" var="traducciones"
	scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Not Found!!!</title>
</head>
<body>
	<h3>Error 404</h3>
	<img src="<%=request.getContextPath()%>/imgs/logo.png"></img>
	<a href="<%=request.getContextPath()%>"><fmt:message key="inicio"
			bundle="${traducciones}" /></a>
</body>
</html>