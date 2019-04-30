
<%@include file="/html/common/header.jsp"%>

<div id="detalle">
	<% 
			
			Contenido contenido =(Contenido) request.getAttribute(AttributeNames.CONTENIDO);
			
			
		%>
	<div id="titulo">
	<h1><%=contenido.getTitulo()%></h1>
	<img src="<%=contenido.getPortada()%>" width="500px">
	</div>
	<div id="info">
	<p><fmt:message key = "fechalanzamiento" bundle="${traducciones}"/>: <%=contenido.getFechaLanzamiento()%></p>
	<p><fmt:message key = "restriccionedad" bundle="${traducciones}"/>: <%=contenido.getRestriccionEdad()%></p>
	<p><fmt:message key = "descripcion" bundle="${traducciones}"/>: <%=contenido.getDescripcionBreve()%></p>
	<p><fmt:message key = "precio" bundle="${traducciones}"/>: <%=contenido.getPrecio()%> $</p>
	<p><%=contenido.getPorcentaje()%>%</p>
	<p> - <%=contenido.getPrecioDescontado()%>$</p>
	<p> <fmt:message key = "duracion" bundle="${traducciones}"/>: <%=contenido.getDuracion()%></p>
	<p> <fmt:message key = "reparto" bundle="${traducciones}"/>: </p>
	<div class="addCarrito">
			<form action="<%=ControllerPaths.CARRITO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.ANADIR%>"/>
			<input type="hidden" name="<%=ParameterNames.ID%>" value="<%=contenido.getIdContenido()%>"/>
			<input type="submit" value="<fmt:message key = "anadircarrito" bundle="${traducciones}"/>"/>
			</form>
	</div>
	</div>
	
	
	
	
</div>
<%@include file="/html/common/footer.jsp"%>