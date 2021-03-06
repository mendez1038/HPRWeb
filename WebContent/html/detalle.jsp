<%@include file="/html/common/header.jsp"%>
	<%
		Contenido contenido = (Contenido) request.getAttribute(AttributeNames.CONTENIDO);
	%>
<main>
<div id="detalle">
	<div id="titulo">
		<h1><%=contenido.getTitulo()%></h1>
		<img src="<%=contenido.getPortada()%>" width="500px" height="600px">
	</div>
	<div id="info">
		<p>
			<fmt:message key="fechalanzamiento" bundle="${traducciones}" />
			:
			<%=contenido.getFechaLanzamiento()%></p>
		<p>
			<fmt:message key="restriccionedad" bundle="${traducciones}" />
			:
			<%=contenido.getRestriccionEdad()%></p>
		<p>
			<fmt:message key="descripcion" bundle="${traducciones}" />
			:
			<%=contenido.getDescripcionBreve()%></p>
		<p><%=contenido.getPorcentaje()%>%
			<fmt:message key="precio" bundle="${traducciones}" />
			:
			<%=contenido.getPrecio()%>
			$ -
			<%=contenido.getPrecioDescontado()%>$
		</p>
		<p>
			<fmt:message key="duracion" bundle="${traducciones}" />
			:
			<%=contenido.getDuracion()%></p>
		<p>
			<fmt:message key="reparto" bundle="${traducciones}" />
			:
		</p>
		<div class="addCarrito">
			<form action="<%=ControllerPaths.CARRITO%>" method="post">
				<input type="hidden" name="<%=ParameterNames.ACTION%>"
					value="<%=Actions.ANADIR%>" /> <input type="hidden"
					name="<%=ParameterNames.ID%>"
					value="<%=contenido.getIdContenido()%>" /> <input type="submit"
					value="<fmt:message key = "anadircarrito" bundle="${traducciones}"/>" />
			</form>
		</div>
	</div>
</div>
</main>
<%@include file="/html/common/footer.jsp"%>