<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.hpr.web.controller.*, com.hpr.web.model.*, com.david.training.model.*, com.david.training.service.*, com.hpr.web.util.*, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope['user-locale']}" scope="session" />
<fmt:setBundle basename="resources.Messages" var="traducciones"
	scope="session" />


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset='UTF-8'">
<title>HPR WEB</title>
<link
	href="https://fonts.googleapis.com/css?family=Lato|Montserrat&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/imgs/logo.ico">
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/css/main.css">
<script src="<%=request.getContextPath()%>/js/14.9.5.min.js"></script>
</head>
<%
List<TipoContenido> tipos = (List<TipoContenido>) request.getAttribute(AttributeNames.TIPOS);
Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
if (errors != null) {
	errors = new Errors();
}
%>
<body>
	<div id="body">
		<div class="tpl-header">
			<div id="header">
				<div id="nav">
					<ul class="menu">
						<li><a id="logo" href="<%=request.getContextPath()%>"> <img
								src="<%=request.getContextPath()%>/imgs/logo.png"
								alt="Logo HPRWEB" width="85px">
						</a></li>
						<li><a href="<%=request.getContextPath()%>"><fmt:message
									key="inicio" bundle="${traducciones}" /></a></li>
						<li><a href="<%=request.getContextPath()%>/biblioteca"><fmt:message
									key="lista" bundle="${traducciones}" /></a></li>
						<li><a
							href="<%=request.getContextPath() + ControllerPaths.FAVORITOS%>"><fmt:message
									key="favoritos" bundle="${traducciones}" /></a></li>
						<li><a
							href="<%=request.getContextPath() + ViewPaths.CARRITO%>"><fmt:message
									key="carrito" bundle="${traducciones}" /></a></li>
						<li>
							<form action="${pageContext.request.contextPath}/contenido" method="get">
								<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR%>" /> 
								<input id="buscador" type="text" name="<%=ParameterNames.TITULO%>" value="<%=ParameterUtils.getParameter(request, ParameterNames.TITULO)%>" 
										placeholder="<fmt:message key = "busqueda" bundle="${traducciones}"/>" />
								<input class="boton" type="submit" value="<fmt:message key = "buscar" bundle="${traducciones}"/>">
								<div id="menudes" class="menudes-contido">
									<div class="filtro">
										<label><fmt:message key="restriccionedad"
												bundle="${traducciones}" /></label> <select
											name="<%=ParameterNames.RESTRICCION_EDAD%>">
											<option value="null"></option>
											<option value="TP"
												<%="TP".equals(request.getParameter(ParameterNames.RESTRICCION_EDAD)) ? "selected='selected'" : ""%>>
												<fmt:message key="todospublicos" bundle="${traducciones}" />
											</option>
											<option value="7+"
												<%="7+".equals(request.getParameter(ParameterNames.RESTRICCION_EDAD)) ? "selected='selected'" : ""%>>
												7+</option>
											<option value="12+"
												<%="12+".equals(request.getParameter(ParameterNames.RESTRICCION_EDAD)) ? "selected='selected'" : ""%>>
												12+</option>
											<option value="16+"
												<%="16+".equals(request.getParameter(ParameterNames.RESTRICCION_EDAD)) ? "selected='selected'" : ""%>>
												16+</option>
											<option value="18+"
												<%="18+".equals(request.getParameter(ParameterNames.RESTRICCION_EDAD)) ? "selected='selected'" : ""%>>
												18+</option>
										</select>

									</div>
									<div class="filtro">
										<label><fmt:message key="tipocontenido"
												bundle="${traducciones}" /></label> <select
											name="<%=ParameterNames.TIPO_CONTENIDO%>">
											<option value=""></option>
											<%
											String tipoSeleccionado = request.getParameter(ParameterNames.TIPO_CONTENIDO);
											for (TipoContenido tc : tipos) {
												String idTipoStr = String.valueOf(tc.getIdTipoContenido());
											%>
											<option value="<%=idTipoStr%>"
												<%=idTipoStr.equals(tipoSeleccionado) ? "selected='selected'" : ""%>>
												<%=tc.getNombreContenido()%>
											</option>
											<%
											}
											%>
										</select>

									</div>
									<div class="filtro">
										<input id="buscador2" type="text"
											name="<%=ParameterNames.ARTISTA%>"
											value="<%=ParameterUtils.getParameter(request, ParameterNames.ARTISTA)%>"
											placeholder="<fmt:message key = "busqueda2" bundle="${traducciones}"/>" />
									</div>
								</div>
							</form>
						</li>

						<li>
							<button class="boton" onclick="desplegarMenu()">
								<fmt:message key="filtros" bundle="${traducciones}" />
							</button>
						</li>

						<c:choose>
							<c:when test="${not empty sessionScope['user']}">

								<li id="user"><a>${sessionScope['user'].email}</a>
									<ul class="submenu">
										<li><a
											href="<%=request.getContextPath() + ViewPaths.PERFIL%>"><fmt:message
													key="cuenta" bundle="${traducciones}" /></a></li>
										<li><a href="<%=request.getContextPath()%>/pedidos"><fmt:message
													key="historial" bundle="${traducciones}" /></a></li>
										<li><a
											href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.LOGOUT%>"><fmt:message
													key="cerrarsesion" bundle="${traducciones}" /></a></li>
										<li><a
											href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.ELIMINAR_CUENTA%>">Eliminar
												Cuenta</a></li>
									</ul></li>
							</c:when>
							<c:otherwise>
								<li>
									<form method="get">
										<button class="boton" type="submit"
											formaction="<%=request.getContextPath() + ViewPaths.LOGIN%>">
											<fmt:message key="iniciosesion" bundle="${traducciones}" />
										</button>
									</form>
								</li>
								<li>
									<form method="get">
										<button class="boton" type="submit"
											formaction="<%=request.getContextPath() + ViewPaths.REGISTRO%>">
											<fmt:message key="registro" bundle="${traducciones}" />
										</button>
									</form>
								</li>
							</c:otherwise>
						</c:choose>
					</ul>

				</div>

			</div>
		</div>