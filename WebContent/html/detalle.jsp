
<%@include file="/html/common/header.jsp"%>

<div id="detalle">
	<% 
			
			Contenido contenido =(Contenido) request.getAttribute(AttributeNames.CONTENIDO);
			
			
		%>
	
	<h1><%=contenido.getTitulo()%></h1>
	<p><%=contenido.getFechaLanzamiento()%></p>
	<p><%=contenido.getRestriccionEdad()%></p>
	<p><%=contenido.getDescripcionBreve()%></p>
	<p><%=contenido.getPrecio()%></p>
	<p><%=contenido.getPrecioDescontado()%></p>
	<p><%=contenido.getPrecio()%></p>
	<p><%=contenido.getDuracion()%></p>
	<p><%=contenido.getTipoContenido()%></p>
	<p><%=contenido.getPorcentaje()%></p>
	
	
	
</div>
<%@include file="/html/common/footer.jsp"%>