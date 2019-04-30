<%@ page import="java.util.List" %>
<%@include file="/html/common/header.jsp"%>

<div id="buscador-resultados">

	<c:set var="urlparams" value="${sessionScope['url']}" scope="request"/>
	<c:set var="resultados" value="${requestScope.resultados}" />
	<p><fmt:message key = "resultados" bundle="${traducciones}"/></p>
	<c:if test="${not empty resultados}">
			
			<ul>
			
			<c:forEach items="${resultados}" var="r">
					
					<c:url var="urlDetalle" scope="page" value="/contenido">
						<c:param name="action" value="<%=Actions.BUSCAR_ID %>"/>
						 <c:param name="id" value="${r.idContenido}"/> 
					</c:url>
					<div class=preview>
					<a href="${urlDetalle}">${r.titulo}</a>
					</div>
			</c:forEach>
			
			</ul>
			
			
	</c:if>
</div>

<%@include file="/html/common/footer.jsp"%>