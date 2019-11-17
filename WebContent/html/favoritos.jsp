<%@include file="/html/common/header.jsp"%>
<main>
<div id="favoritos">

	<%
		List<Contenido> favoritos = (List<Contenido>) request.getAttribute(AttributeNames.FAVORITOS);
	
	%>
	<h2>
		<fmt:message key="favoritos" bundle="${traducciones}" />
	</h2>
	<%
	
			
		if (favoritos!=null && !favoritos.isEmpty()) {
			%>
	<%
			for (Contenido resultado: favoritos) {
				%>
	<div class="preview">
		<a
			href="<%=ControllerPaths.CONTENIDO%>?
						<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=
						<%=resultado.getIdContenido()%>">
			<%=resultado.getTitulo()%></a>

	</div>
	<%}%>
	<p>
	<center>
		<c:url var="urlBase" value="/index" scope="page"></c:url>

		<!-- A la anterior pagina -->
		<c:if test="${page > 1}">
			<a href="${urlBase}?page=${page - 1}"> <fmt:message
					key="anterior" bundle="${traducciones}" />
			</a>
							&nbsp;&nbsp;
						</c:if>

		<c:if test="${totalPages > 1}">

			<c:if test="${firstPagedPage > 2}">
				<a href="${urlBase}?page=1"><b>1</b></a>
				<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
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
				<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
				<a href="${urlBase}?page=${totalPages}"><b>${totalPages}</b></a>
			</c:if>

		</c:if>

		<!-- A la siguiente página -->
		<c:if test="${page < totalPages}">
							&nbsp;&nbsp;		
							<a href="${urlBase}?page=${page + 1}"> <fmt:message
					key="siguiente" bundle="${traducciones}" />
			</a>
		</c:if>

	</center>
	</p>
	<%}%>

</div>
</main>
<%@include file="/html/common/footer.jsp"%>