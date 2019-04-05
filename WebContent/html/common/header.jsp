<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.hpr.web.controller.*, com.hpr.web.model.*, com.david.training.model.*, com.hpr.web.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
		

		<div id="nav">
			<a id="logo" href="<%=request.getContextPath()+ViewPaths.HOME%>">
				<img src="<%=request.getContextPath()%>/imgs/logo.png" width="80px"height="48px" alt="Logo HPRWEB">
			</a>	
			<ul class="menu">
				<li><a href="<%=request.getContextPath()+ViewPaths.HOME%>">Home</a></li>
				<li><a href="#">Lista</a></li>
				<li><a href="#">Favoritos</a></li>
				<li><a href="#">Carrito</a></li>
				<li>
					<form action="<%=ControllerPaths.CONTENIDO%>" method="post">
						<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR%>" /> 
						<input type="text" name="<%=ParameterNames.TITULO%>" value="<%=ParameterUtils.getParameter(request, ParameterNames.NOMBRE)%>"placeholder="Título, artista, director..." />
						<button type="submit">Buscar</button>
					</form>
					<button >Filtros</button>
				</li>

		<c:choose>
		<c:when test="${not empty sessionScope['user']}">	
			
				<li id="user"><a>${sessionScope['user'].email}</a>
					<ul class="submenu">
						<li><a href="#">Cuenta</a></li>
						<li><a
							href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>">Salir</a></li>
					</ul></li>
		</c:when>
		<c:otherwise>
				
				<form method="get">
					<button type="submit" formaction="<%=request.getContextPath() + ViewPaths.LOGIN%>">Iniciar Sesion</button> 
				</form>
				<form action="get">
					<button type="submit" formaction="<%=request.getContextPath() + ViewPaths.REGISTRO%>">Registrate</button>
				</form>
		</c:otherwise>
		</c:choose>
			</ul>

		</div>

	</div>