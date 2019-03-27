<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.hpr.web.controller.*, com.hpr.web.model.*, com.david.training.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HPR WEB</title>
<link rel="icon" href="<%=request.getContextPath()%>/imgs/logo.ico">
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/css/login.css">

</head>
<%
	Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new Errors();
%>
<body>

	<div id="header">
		

		<div id="nav">
			<a id="logo" href="<%=request.getContextPath()%>">
				<img src="<%=request.getContextPath()%>/imgs/logo.png" width="80px"height="48px" alt="Logo HPRWEB">
			</a>	
			<ul class="menu">
				<li><a href="#">Home</a></li>
				<li><a href="#">Lista</a></li>
				<li><a href="#">Favoritos</a></li>
				<li><a href="#">Carrito</a></li>
				<li>
					<form action="<%=ControllerPaths.CONTENIDO%>" method="get">
						<input type="hidden" name="<%=ParameterNames.ACTION%>"
							value="<%=Actions.BUSCAR%>" /> <input type="text"
							name="<%=ParameterNames.TITULO%>" placeholder="Título, artista, director..." />
						<button type="submit">Buscar</button>
						<button >Filtros</button>
					</form>
				</li>

				<%
		Usuario u = (Usuario) request.getSession().getAttribute(SessionAttributeNames.USER);
		if (u != null) {
			
			%>
				<li id="user"><a><%=u.getEmail()%></a>
					<ul class="submenu">
						<li><a href="#">Cuenta</a></li>
						<li><a
							href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>">Salir</a></li>
					</ul></li>
			<%
				}
			%>
			</ul>

		</div>

	</div>