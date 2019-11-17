<%@ page import="java.util.List" %>
<%@include file="/html/common/header.jsp"%>
<main>
<div id="buscador-resultados">


	<c:set var="resultados" value="${requestScope.resultados}" />
	<h1>
		<fmt:message key="resultados" bundle="${traducciones}"></fmt:message>
	</h1>
	<c:if test="${not empty resultados}">

		<ul>

			<c:forEach items="${resultados}" var="r">

				<c:url var="urlDetalle" scope="page" value="/contenido">
					<c:param name="action" value="buscar_id" />
					<c:param name="id" value="${r.idContenido}" />
				</c:url>
				<div class="preview">
					<a title="${r.titulo}" href="${urlDetalle}"> <img
						src="${r.portada}" alt="${r.titulo}"></a>
				</div>
			</c:forEach>

		</ul>

		<div class="paginacion">
			<p>
			<center>
				<c:url var="urlBase" value="/contenido" scope="page">
					<c:param name="action" value="buscar" />
				</c:url>

				<!-- A la anterior pagina -->
				<c:if test="${page > 1}">
					<a href="${urlBase}${url}&page=${page - 1}"> <fmt:message
							key="anterior" bundle="${traducciones}" />
					</a>
							&nbsp;&nbsp;
						</c:if>

				<c:if test="${totalPages > 1}">

					<c:if test="${firstPagedPage > 2}">
						<a href="${urlBase}${url}&page=1"><b>1</b></a>
						<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
					</c:if>

					<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
						<c:choose>
							<c:when test="${page != i}">
										&nbsp;<a href="${urlBase}${url}&page=${i}"><b>${i}</b></a>&nbsp;
								  </c:when>
							<c:otherwise>
										&nbsp;<b>${i}</b>&nbsp;
								  </c:otherwise>
						</c:choose>
					</c:forEach>

					<c:if test="${lastPagedPage < totalPages-1}">
						<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b>
						<a href="${urlBase}${url}&page=${totalPages}"><b>${totalPages}</b></a>
					</c:if>

				</c:if>

				<!-- A la siguiente página -->
				<c:if test="${page < totalPages}">
							&nbsp;&nbsp;		
							<a href="${urlBase}${url}&page=${page + 1}"> <fmt:message
							key="siguiente" bundle="${traducciones}" />
					</a>
				</c:if>

			</center>
			</p>
		</div>



	</c:if>
</div>
</main>
<%@include file="/html/common/footer.jsp"%>