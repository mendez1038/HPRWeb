<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.hpr.web.controller.*, com.hpr.web.model.*, com.david.training.model.*, com.hpr.web.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope['user-locale']}" scope="session"/>
<fmt:setBundle basename="resources.Messages" var="traducciones" scope="session"/>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HPR WEB</title>
<link rel="icon" href="<%=request.getContextPath()%>/imgs/logo.ico">
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/css/main.css">

</head>
<%
	Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new Errors();
%>
<body>

	<div id="header">
		
		<a id="logo" href="<%=request.getContextPath()%>">
				<img src="<%=request.getContextPath()%>/imgs/logo.png" width="80px"height="49px" alt="Logo HPRWEB">
			</a>
		<div id="nav">
				
			<ul class="menu">
				<li><a href="<%=request.getContextPath()%>"><fmt:message key = "inicio" bundle="${traducciones}"/></a></li>
				<li><a href="#"><fmt:message key = "lista" bundle="${traducciones}"/></a></li>
				<li><a href="#"><fmt:message key = "favoritos" bundle="${traducciones}"/></a></li>
				<li><a href="<%=request.getContextPath()+ViewPaths.CARRITO%>"><fmt:message key = "carrito" bundle="${traducciones}"/></a></li>
				<li>
					<form action="<%=ControllerPaths.CONTENIDO%>" method="post">
						<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR%>" /> 
						<input id="buscador" type="text" name="<%=ParameterNames.TITULO%>" value="<%=ParameterUtils.getParameter(request, ParameterNames.TITULO)%>"placeholder="<fmt:message key = "busqueda" bundle="${traducciones}"/>" />
						<input class="boton" type="submit" value="<fmt:message key = "buscar" bundle="${traducciones}"/>">
					</form>
				</li>
				<li>
					<button class="boton"><fmt:message key = "filtros" bundle="${traducciones}"/></button>
				</li>

		<c:choose>
		<c:when test="${not empty sessionScope['user']}">	
			
				<li id="user"><a>${sessionScope['user'].email}</a>
					<ul class="submenu">
						<li><a href="<%=request.getContextPath()+ViewPaths.PERFIL%>"><fmt:message key = "cuenta" bundle="${traducciones}"/></a></li>
						<li><a
							href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>"><fmt:message key = "cerrarsesion" bundle="${traducciones}"/></a></li>
					</ul></li>
		</c:when>
		<c:otherwise>
				<li>
				<form method="get">
					<button class="boton" type="submit" formaction="<%=request.getContextPath() + ViewPaths.LOGIN%>"><fmt:message key = "iniciosesion" bundle="${traducciones}"/></button> 
				</form>
				</li>
				<li>
				<form method="get">
					<button class="boton" type="submit" formaction="<%=request.getContextPath() + ViewPaths.REGISTRO%>"><fmt:message key = "registro" bundle="${traducciones}"/></button>
				</form>
				</li>
		</c:otherwise>
		</c:choose>
			</ul>

		</div>

	</div>