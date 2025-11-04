<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/html/common/header.jsp"%>
<main>
	<div class="tpl-carrito-hp">
		<div class="tpl-carrito-hp_max">
			<c:set var="carrito" value='${sessionScope["carrito"]}'
				scope="session" />
			<div class="tpl-carrito-hp_titulo">
				<fmt:message key="carrito" bundle="${traducciones}"></fmt:message>
			</div>

			<c:choose>
				<c:when test="${carrito!=null}">
					<c:forEach items="${carrito.getLineas()}" var="lineas">
						<div class="tpl-carrito-hp_lineacarrito">
							<img src="${lineas.getContenido().getPortada()}" width="50px">
							<span> ${lineas.getContenido().getTitulo()} <fmt:message
									key="precio" bundle="${traducciones}" />:
									<c:if test="${lineas.getContenido().getPrecioDescontado() > 0}">
									
								${lineas.getContenido().getPrecio() - lineas.getContenido().getPrecioDescontado()} €
								
								</c:if><c:if test="${lineas.getContenido().getPrecioDescontado() <= 0}">
								${lineas.getContenido().getPrecio()} € 
								</c:if> <a
								class="enlace"
								href="<%=ControllerPaths.CARRITO %>?<%=ParameterNames.ACTION%>=<%=Actions.ELIMINAR%>&amp;<%=ParameterNames.ID%>=${lineas.getContenido().getIdContenido()}">X</a>
							</span>
						</div>
					</c:forEach>
					<div class="tpl-carrito-hp_carritofinal">
						<fmt:message key="total" bundle="${traducciones}" />
						: ${carrito.getTotal()} €
					</div>
				</c:when>
				<c:otherwise>
					<div class="tpl-carrito-hp_nocarrito">
						<fmt:message key="nocarrito" bundle="${traducciones}" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</main>
<%@include file="/html/common/footer.jsp"%>