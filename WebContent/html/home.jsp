<%@ page import="com.hpr.web.controller.*, com.david.training.service.*, com.david.training.model.*,java.util.List "%>

<%@include file="/html/common/header.jsp"%>

<div id="home">

	<%
		List<Contenido> all= (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS_VENTAS);
		List<Contenido> rebajas= (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS_REBAJAS);
		List<Contenido> novedades= (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS_NOVEDADES);
	%>
	
	
		<h3><fmt:message key = "bienvenida" bundle="${traducciones}"/></h3>	
		
		<h2><fmt:message key = "novedades" bundle="${traducciones}"/></h2>
		<%
	
			
		if (novedades!=null && !novedades.isEmpty()) {
			%>
			<%
			for (Contenido resultado: novedades) {
				%>
				<div class="preview">
				<a href="<%=ControllerPaths.CONTENIDO%>?
						<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=
						<%=resultado.getIdContenido()%>">
						<%=resultado.getTitulo()%></a>
				
				</div>
				<%}%>
				<p><center>
						<c:url var="urlBase" value="/index" scope="page"></c:url>
					
						<!-- A la anterior pagina -->
						<c:if test="${page > 1}">
							<a href="${urlBase}?page=${page - 1}">
								<fmt:message key="anterior" bundle="${traducciones}"/>
							</a>
							&nbsp;&nbsp;
						</c:if>
					
						<c:if test="${totalPages > 1}">	
					
							<c:if test="${firstPagedPage > 2}">
								<a href="${urlBase}?page=1"><b>1</b></a><b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b> 
							</c:if>
						
							<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
								<c:choose>
								  <c:when test="${page != i}">
										&nbsp;<a href="${urlBase}?page=${i}"><b>${i}</b></a>&nbsp;
								  </c:when>
								  <c:otherwise>
										&nbsp;<b>${i}</b>&nbsp;
								  </c:otherwise>
								</c:choose>
							</c:forEach>
					
							<c:if test="${lastPagedPage < totalPages-1}">
								<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b><a href="${urlBase}?page=${totalPages}"><b>${totalPages}</b></a>
							</c:if>	
					
						</c:if>
					
						<!-- A la siguiente página -->	
						<c:if test="${page < totalPages}">
							&nbsp;&nbsp;		
							<a href="${urlBase}?page=${page + 1}">
								<fmt:message key="siguiente" bundle="${traducciones}"/>
							</a>			
						</c:if>	
					
						</center></p>
			<%}%>
		
		<h2><fmt:message key = "masvendidos" bundle="${traducciones}"/></h2>
		<%
	
			
		if (all!=null && !all.isEmpty()) {
			%>
			<%
			for (Contenido resultado: all) {
				%>
				<div class="preview">
				<a href="<%=ControllerPaths.CONTENIDO%>?
						<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=
						<%=resultado.getIdContenido()%>">
						<%=resultado.getTitulo()%></a>
				
				</div>
				<%}%>
				<p><center>
						<c:url var="urlBase" value="/index" scope="page"></c:url>
					
						<!-- A la anterior pagina -->
						<c:if test="${page > 1}">
							<a href="${urlBase}?page=${page - 1}">
								<fmt:message key="anterior" bundle="${traducciones}"/>
							</a>
							&nbsp;&nbsp;
						</c:if>
					
						<c:if test="${totalPages > 1}">	
					
							<c:if test="${firstPagedPage > 2}">
								<a href="${urlBase}?page=1"><b>1</b></a><b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b> 
							</c:if>
						
							<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
								<c:choose>
								  <c:when test="${page != i}">
										&nbsp;<a href="${urlBase}?page=${i}"><b>${i}</b></a>&nbsp;
								  </c:when>
								  <c:otherwise>
										&nbsp;<b>${i}</b>&nbsp;
								  </c:otherwise>
								</c:choose>
							</c:forEach>
					
							<c:if test="${lastPagedPage < totalPages-1}">
								<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b><a href="${urlBase}?page=${totalPages}"><b>${totalPages}</b></a>
							</c:if>	
					
						</c:if>
					
						<!-- A la siguiente página -->	
						<c:if test="${page < totalPages}">
							&nbsp;&nbsp;		
							<a href="${urlBase}?page=${page + 1}">
								<fmt:message key="siguiente" bundle="${traducciones}"/>
							</a>			
						</c:if>	
					
						</center></p>
					
		<%}%>
		<h2><fmt:message key = "rebajas" bundle="${traducciones}"/></h2>
		<%
	
			
		if (rebajas!=null && !rebajas.isEmpty()) {
			%>
			<%
			for (Contenido resultado: rebajas) {
				%>
				<div class="preview">
				<a href="<%=ControllerPaths.CONTENIDO%>?
						<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=
						<%=resultado.getIdContenido()%>">
						<%=resultado.getTitulo()%></a>
				
				</div>
				<%}%>
				<p><center>
						<c:url var="urlBase" value="/index" scope="page"></c:url>
					
						<!-- A la anterior pagina -->
						<c:if test="${page > 1}">
							<a href="${urlBase}?page=${page - 1}">
								<fmt:message key="anterior" bundle="${traducciones}"/>
							</a>
							&nbsp;&nbsp;
						</c:if>
					
						<c:if test="${totalPages > 1}">	
					
							<c:if test="${firstPagedPage > 2}">
								<a href="${urlBase}?page=1"><b>1</b></a><b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b> 
							</c:if>
						
							<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
								<c:choose>
								  <c:when test="${page != i}">
										&nbsp;<a href="${urlBase}?page=${i}"><b>${i}</b></a>&nbsp;
								  </c:when>
								  <c:otherwise>
										&nbsp;<b>${i}</b>&nbsp;
								  </c:otherwise>
								</c:choose>
							</c:forEach>
					
							<c:if test="${lastPagedPage < totalPages-1}">
								<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b><a href="${urlBase}?page=${totalPages}"><b>${totalPages}</b></a>
							</c:if>	
					
						</c:if>
					
						<!-- A la siguiente página -->	
						<c:if test="${page < totalPages}">
							&nbsp;&nbsp;		
							<a href="${urlBase}?page=${page + 1}">
								<fmt:message key="siguiente" bundle="${traducciones}"/>
							</a>			
						</c:if>	
					
						</center></p>
			<%}%>	
		
</div>

<%@include file="/html/common/footer.jsp"%>