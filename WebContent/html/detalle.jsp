<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/html/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<main>
	<div class="detalle">
		<div class="detalle_max">
			<div class="titulo">
				<h2>${contenido.titulo}</h2>
				<img src="${contenido.portada}" alt="${contenido.titulo}">
			</div>
			<div class="info">
				<p>
					<fmt:message key="fechalanzamiento" bundle="${traducciones}" />
					:${contenido.fechaLanzamiento}
				</p>
				<p>
					<fmt:message key="restriccionedad" bundle="${traducciones}" />
					:${contenido.restriccionEdad}
				</p>
				<p>
					<fmt:message key="descripcion" bundle="${traducciones}" />
					: ${contenido.descripcionBreve}
				</p>


				<c:if test="${contenido.porcentaje > 0}">

					<p>
						<fmt:message key="descuento" bundle="${traducciones}" />
						: ${contenido.porcentaje}%
					</p>
					<p>
						<fmt:message key="precio" bundle="${traducciones}" />
						${contenido.precio - contenido.precioDescontado} €
					</p>
				</c:if>
				<c:if test="${contenido.porcentaje <= 0}">
					<p>
						<fmt:message key="precio" bundle="${traducciones}" />
						: ${contenido.precio}
					</p>
				</c:if>


				<p>
					<fmt:message key="duracion" bundle="${traducciones}" />
					: ${contenido.duracion}
				</p>
				<p>
					<fmt:message key="reparto" bundle="${traducciones}" />
				</p>


				<c:choose>
					<c:when test="${empty contenido.artistasRoles}">
						<p>
							<fmt:message key="reparto-info" bundle="${traducciones}" />
						</p>
					</c:when>
					<c:otherwise>
						<table class="tabla-reparto">
							<thead>
								<tr>
									<th><fmt:message key="rol" bundle="${traducciones}" /></th>
									<th><fmt:message key="artista" bundle="${traducciones}" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="ar" items="${contenido.artistasRoles}">
									<tr>
										<td>${ar.nombreRol}</td>
										<td>${ar.nombreArtista}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
				<div class="addCarrito">

					<c:choose>
						
						<c:when test="${not empty sessionScope['user'] and comprado}">
							<a class="btn btn-primary" href=""> Ver contenido </a>
						</c:when>
						
						<c:otherwise>
							<form action="<%=ControllerPaths.CARRITO%>" method="post">
								<input type="hidden" name="<%=ParameterNames.ACTION%>"
									value="<%=Actions.ANADIR%>" /> <input type="hidden"
									name="<%=ParameterNames.ID%>" value="${contenido.idContenido}" />
								<input type="submit"
									value="<fmt:message key = "anadircarrito" bundle="${traducciones}"/>" />
							</form>
							
							<c:if test="${not empty sessionScope['user']}">
								<form action="/HPRWEB<%=ControllerPaths.FAVORITOS%>" method="post">
									<input type="hidden" name="<%=ParameterNames.ID%>"
										value="${contenido.idContenido}" />
									<c:choose>
										<c:when test="${es_favorito}">
											<input type="hidden" name="<%=ParameterNames.ACTION%>"
												value="<%=Actions.ELIMINAR_FAVORITO%>" />
											<input type="submit"
												value="💔 <fmt:message key = "eliminarfavoritos" bundle="${traducciones}"/>"></input>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="<%=ParameterNames.ACTION%>"
												value="<%=Actions.ANADIR_FAVORITO%>" />
											<input type="submit"
												value="❤️ <fmt:message key = "anadirfavoritos" bundle="${traducciones}"/>"></input>
										</c:otherwise>
									</c:choose>
								</form>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</main>
<%@include file="/html/common/footer.jsp"%>