
<%@include file="/html/common/header.jsp"%>
<div id="carrito">
	<c:set var="carrito" value='${sessionScope["carrito"]}' scope="session"/>
	<c:choose>
		<c:when test="${carrito!=null}">
			<c:forEach items="${carrito.getLineas()}" var="lineas">
                <div class="lineacarrito">
                   	<%-- <img src="<%=request.getContextPath()%>/imgs/icojuego/<%=lineaCarrito.getContenido().getIdContenido()%>.jpg"></img> --%>
                    <div>${lineas.getContenido().getTitulo()}</div>
                    <div><span>${lineas.getContenido().getPrecio()} $</span></div>
                    <a class="enlace" href="<%=ControllerPaths.CARRITO %>?<%=ParameterNames.ACTION%>=<%=Actions.ELIMINAR%>&amp;${lineas.getContenido().getIdContenido()}">X</a>
                </div>
			</c:forEach>
		</c:when>
	</c:choose>
	<div class="carritofinal">
				<div>Total: ${carrito.getTotal()} $</div>
			</div>

		
</div>

<%@include file="/html/common/footer.jsp"%>