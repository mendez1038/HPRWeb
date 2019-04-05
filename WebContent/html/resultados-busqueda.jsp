<%@ page import="com.hpr.web.controller.Actions"%>
    
<div id="buscador-resultados">	
	
	<c:if test="${not empty resultados.page}">
			<p>Resultados:</p>
			<ul>
			<c:forEach items="${resultados}" var="r">
					
					<c:url var="urlDetalle" scope="page" value="contenido">
						<c:param name="action" value="<%=Actions.BUSCAR_ID %>"/>
						<c:param name="id" value="${r.id}"/>
					</c:url>
					<li>
					<a href="${urlDetalle}"><${r.titulo}</a>
					</li>
			</c:forEach>
			</ul>
			
	
	</c:if>
</div>	