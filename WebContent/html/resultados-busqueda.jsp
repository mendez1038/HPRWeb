<%@ page import="java.util.*, com.david.training.model.*, com.hpr.web.util.*, com.hpr.web.controller.*, com.david.training.service.*"%>
    
<div id="buscador-resultados">	
	<%		
		List<Contenido> resultados = (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS);	
		if (resultados!=null && !resultados.isEmpty()) {
			%>
			<p>Resultados:</p>
			<ul><%
			for (Contenido resultado: resultados) {
				%>
					<li><a href="<%=ControllerPaths.CONTENIDO%>?
							<%=ParameterNames.ACTION%>=
							<%=Actions.BUSCAR%>"><%=resultado.getTitulo()%></a></li>
				<%
			}
			%></ul><%
		}
	%>
</div>	