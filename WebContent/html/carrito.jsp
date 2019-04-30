
<%@include file="/html/common/header.jsp"%>
<div id="carrito">
	<c:set var="carrito" value='${sessionScope["carrito"]}' scope="session"/>
	<c:choose>
		<c:when test="${carrito!=null}">
			<c:forEach items="${carrito.getLineas()}" var="lineas">
                <div class="lineacarrito">
                   	<%-- <img src="<%=request.getContextPath()%>/imgs/icojuego/
                   	<%=lineaCarrito.getContenido().getIdContenido()%>.jpg"></img> --%>
                    <div>${lineas.getContenido().getTitulo()}</div>
                    <div><span><fmt:message key = "precio" bundle="${traducciones}"/>: ${lineas.getContenido().getPrecio()} $</span>
                    <span>- ${lineas.getContenido().getPrecioDescontado()} $</span></div>
                    <a class="enlace" href="<%=ControllerPaths.CARRITO %>?<%=ParameterNames.ACTION%>=<%=Actions.ELIMINAR%>&amp;<%=ParameterNames.ID%>=${lineas.getContenido().getIdContenido()}">X</a>
                </div>
                <div class="carritofinal">
			<fmt:message key = "total" bundle="${traducciones}"/>: ${carrito.getTotal()} $
			</div>
			</c:forEach>
			
		</c:when>
		<c:when test="${carrito=null}">
		<div><p><fmt:message key = "nocarrito" bundle="${traducciones}"/></p></div>
		</c:when>
		
	</c:choose>
	
			
				
	

		
</div>

<%@include file="/html/common/footer.jsp"%>