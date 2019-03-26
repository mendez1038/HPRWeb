<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.hpr.web.controller.*, com.hpr.web.model.*, com.david.training.model.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HPR WEB</title>
	<link rel="icon" href="<%=request.getContextPath()%>/img/logo.ico">
</head>
<%
	Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new Errors();
%>
<body>

	<div id="header">
		<img src="<%=request.getContextPath()%>/imgs/logo.png" width="100" height="60" alt="Logo HPRWEB"> 
	
		<div id="nav" >
			<ul style="list-style-type:none;">
				<li>Home</li>
				<li>Lista</li>
				<li>Favoritos</li>
				<li>Carrito</li>
			</ul>
		</div>
		
		<div id="user">
		<%
		Usuario u = (Usuario) request.getSession().getAttribute(SessionAttributeNames.USER);
		if (u != null) {
			
			%>
			 <a><%=u.getEmail()%></a>
			 <p><a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>">Salir</a></p> 
			 <%
			}
			 %>
		</div>
		<div id="buscador">
		<form action="<%=ControllerPaths.CONTENIDO%>" method="get">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR%>"/>
			<input type="text" name="<%=ParameterNames.TITULO%>" placeholder="Buscador"/>
			<button type="submit">Buscar</button>
		<!-- 	<input type="button" value="Filtros"/>
		 --></form> 
		</div>
	</div>
