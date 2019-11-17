<%@include file="/html/common/header.jsp"%>
<main>
<div id="carrito">


	<c:set var="carrito" value='${sessionScope["carrito"]}' scope="session" />
	<h1>
		<fmt:message key="carrito" bundle="${traducciones}"></fmt:message>
	</h1>

	<c:choose>
		<c:when test="${carrito!=null}">
			<c:forEach items="${carrito.getLineas()}" var="lineas">
				<div class="lineacarrito">
					<img src="${lineas.getContenido().getPortada()}" width="50px">
					${lineas.getContenido().getTitulo()} <span><fmt:message
							key="precio" bundle="${traducciones}" />:${lineas.getContenido().getPrecio()}
						$</span> <span>- ${lineas.getContenido().getPrecioDescontado()} $</span>
					<a class="enlace"
						href="<%=ControllerPaths.CARRITO %>?<%=ParameterNames.ACTION%>=<%=Actions.ELIMINAR%>&amp;<%=ParameterNames.ID%>=${lineas.getContenido().getIdContenido()}">X</a>
				</div>

			</c:forEach>
			<div class="carritofinal">
				<fmt:message key="total" bundle="${traducciones}" />
				: ${carrito.getTotal()} $
			</div>

		</c:when>
		<c:otherwise>
			<div>
				<p>
					<fmt:message key="nocarrito" bundle="${traducciones}" />
				</p>
			</div>
		</c:otherwise>
	</c:choose>

</div>
</main>
<%@include file="/html/common/footer.jsp"%>