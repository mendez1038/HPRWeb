<%@ page
	import="com.hpr.web.controller.*, com.david.training.service.*, com.david.training.model.*,java.util.List"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/html/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<main>
	<div class="modulo-home">
		<div class="modulo-home_max">
			<%
			List<Contenido> all = (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS_VENTAS);
			List<Contenido> rebajas = (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS_REBAJAS);
			List<Contenido> novedades = (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS_NOVEDADES);

			if (novedades != null && !novedades.isEmpty()) {
			%>
			<div class="tpl-title-hp">
				<div class="sta-title-hp_max">
					<div class="sta-title-hp_slogan">
						<fmt:message key="bienvenida" bundle="${traducciones}" />
					</div>
					<div class="sta-title-hp_titulo">
						Top
						<fmt:message key="novedades" bundle="${traducciones}" />
					</div>
				</div>
			</div>

			<div class="tpl-cajas-hp">
				<ul class="sta-cajas-hp_max">
					<%
					for (Contenido resultado : novedades) {
					%><li class="sta-cajas-hp_caja"><a
						title="<%=resultado.getTitulo()%>"
						href="<%=ControllerPaths.CONTENIDO%>?<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=<%=resultado.getIdContenido()%>">
							<div class="sta-cajas-hp_img"
								style="background-image: url('<%=resultado.getPortada()%>');"></div>
							<%
							if (resultado.getPrecioDescontado() > 0) {
								double nuevoPrecio = resultado.getPrecio() - resultado.getPrecioDescontado();
							%>
							<div class="sta-cajas-hp_titulo sta-descuento">
								<%=resultado.getTitulo()%>
								<div class="sta-cajas-hp_precio">
									<span class="sta-porcentaje sta-descuento"><%=resultado.getPorcentaje()%>%</span>
									<span class="sta-precio sta-descuento"><%=resultado.getPrecio()%></span>
									<span class="sta-nuevo sta-descuento"><%=nuevoPrecio%>€</span>
								</div>
							</div> <%
 } else {
 %>
							<div class="sta-cajas-hp_titulo">
								<%=resultado.getTitulo()%>
								<div class="sta-cajas-hp_precio">
									<span class="sta-nuevo"></span> <span class="sta-porcentaje"></span>
									<span class="sta-precio"><%=resultado.getPrecio()%> €</span>
								</div>
							</div> <%
 }
 %>
					</a></li>
					<%
					}
					%>
				</ul>
				<%
				}
				%>
			</div>
			<div class="paginacion">
				<p>
				<center>
					<c:url var="urlBase" value="/index" scope="page"></c:url>
					<!-- A la anterior pagina -->
					<c:if test="${page > 1}">
						<a href="${urlBase}?page=${page - 1}"> <fmt:message
								key="anterior" bundle="${traducciones}" /></a>&nbsp;&nbsp;
			</c:if>
					<c:if test="${totalPages > 1}">
						<c:if test="${firstPagedPage > 2}">
							<a href="${urlBase}?page=1"><b>1</b></a>
							<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
						</c:if>
						<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}"
							var="i">
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
							<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
							<a href="${urlBase}?page=${totalPages}"><b>${totalPages}</b></a>
						</c:if>
					</c:if>
					<!-- A la siguiente pagina -->
					<c:if test="${page < totalPages}">
				&nbsp;&nbsp;<a href="${urlBase}?page=${page + 1}"> <fmt:message
								key="siguiente" bundle="${traducciones}" /></a>
					</c:if>
				</center>
				</p>
			</div>

			<%
			if (rebajas != null && !rebajas.isEmpty()) {
			%>
			<div class="tpl-title-hp">
				<div class="sta-title-hp_max">
					<div class="sta-title-hp_titulo">
						<fmt:message key="rebajas" bundle="${traducciones}" />
					</div>
				</div>
			</div>
			<div class="tpl-cajas-hp">
				<ul class="sta-cajas-hp_max">
					<%
					for (Contenido resultado : rebajas) {
					%><li class="sta-cajas-hp_caja"><a
						title="<%=resultado.getTitulo()%>"
						href="<%=ControllerPaths.CONTENIDO%>?<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=<%=resultado.getIdContenido()%>">
							<div class="sta-cajas-hp_img"
								style="background-image: url('<%=resultado.getPortada()%>');"></div>
							<%
							if (resultado.getPrecioDescontado() > 0) {
								double nuevoPrecio = resultado.getPrecio() - resultado.getPrecioDescontado();
							%>
							<div class="sta-cajas-hp_titulo sta-descuento">
								<%=resultado.getTitulo()%>
								<div class="sta-cajas-hp_precio">
									<span class="sta-porcentaje sta-descuento"><%=resultado.getPorcentaje()%>%</span>
									<span class="sta-precio sta-descuento"><%=resultado.getPrecio()%></span>
									<span class="sta-nuevo sta-descuento"><%=nuevoPrecio%>€</span>
								</div>
							</div> <%
 } else {
 %>
							<div class="sta-cajas-hp_titulo">
								<%=resultado.getTitulo()%>
								<div class="sta-cajas-hp_precio">
									<span class="sta-nuevo"></span> <span class="sta-porcentaje"></span>
									<span class="sta-precio"><%=resultado.getPrecio()%> €</span>
								</div>
							</div> <%
 }
 %>
					</a></li>
					<%
					}
					%>
				</ul>
				<%
				}
				%>
			</div>
			<div class="paginacion">
				<p>
				<center>
					<c:url var="urlBase" value="/index" scope="page"></c:url>
					<!-- A la anterior pagina -->
					<c:if test="${pageDiscount > 1}">
						<a href="${urlBase}?pageDiscount=${pageDiscount - 1}"> <fmt:message
								key="anterior" bundle="${traducciones}" /></a>&nbsp;&nbsp;
			</c:if>
					<c:if test="${totalPagesDiscount > 1}">
						<c:if test="${firstPagedPageDiscount > 2}">
							<a href="${urlBase}?pageDiscount=1"><b>1</b></a>
							<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
						</c:if>
						<c:forEach begin="${firstPagedPageDiscount}"
							end="${lastPagedPageDiscount}" var="i">
							<c:choose>
								<c:when test="${pageDiscount != i}">
							&nbsp;<a href="${urlBase}?pageDiscount=${i}"><b>${i}</b></a>&nbsp;
						</c:when>
								<c:otherwise>
								&nbsp;<b>${i}</b>&nbsp;
						</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${lastPagedPageDiscount < totalPagesDiscount-1}">
							<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
							<a href="${urlBase}?pageDiscount=${totalPagesDiscount}"><b>${totalPagesDiscount}</b></a>
						</c:if>
					</c:if>
					<!-- A la siguiente pagina -->
					<c:if test="${pageDiscount < totalPagesDiscount}">
				&nbsp;&nbsp;<a href="${urlBase}?pageDiscount=${pageDiscount + 1}">
							<fmt:message key="siguiente" bundle="${traducciones}" />
						</a>
					</c:if>
				</center>
				</p>
			</div>
			<%
			if (all != null && !all.isEmpty()) {
			%>
			<div class="tpl-title-hp">
				<div class="sta-title-hp_max">
					<div class="sta-title-hp_titulo">
						<fmt:message key="masvendidos" bundle="${traducciones}" />
					</div>
				</div>
			</div>

			<div class="tpl-cajas-hp">
				<ul class="sta-cajas-hp_max">
					<%
					for (Contenido resultado : all) {
					%><li class="sta-cajas-hp_caja"><a
						title="<%=resultado.getTitulo()%>"
						href="<%=ControllerPaths.CONTENIDO%>?<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=<%=resultado.getIdContenido()%>">
							<div class="sta-cajas-hp_img"
								style="background-image: url('<%=resultado.getPortada()%>');"></div>
							<%
							if (resultado.getPrecioDescontado() > 0) {
								double nuevoPrecio = resultado.getPrecio() - resultado.getPrecioDescontado();
							%>
							<div class="sta-cajas-hp_titulo sta-descuento">
								<%=resultado.getTitulo()%>
								<div class="sta-cajas-hp_precio">
									<span class="sta-porcentaje sta-descuento"><%=resultado.getPorcentaje()%>%</span>
									<span class="sta-precio sta-descuento"><%=resultado.getPrecio()%></span>
									<span class="sta-nuevo sta-descuento"><%=nuevoPrecio%>€</span>
								</div>
							</div> <%
 } else {
 %>
							<div class="sta-cajas-hp_titulo">
								<%=resultado.getTitulo()%>
								<div class="sta-cajas-hp_precio">
									<span class="sta-nuevo"></span> <span class="sta-porcentaje"></span>
									<span class="sta-precio"><%=resultado.getPrecio()%> €</span>
								</div>
							</div> <%
 }
 %>
					</a></li>
					<%
					}
					%>
				</ul>
				<%
				}
				%>
			</div>
			<div class="paginacion">
				<p>
				<center>
					<c:url var="urlBase" value="/index" scope="page"></c:url>
					<!-- A la anterior pagina -->
					<c:if test="${pageSales > 1}">
						<a href="${urlBase}?pageSales=${pageSales - 1}"> <fmt:message
								key="anterior" bundle="${traducciones}" /></a>&nbsp;&nbsp;
			</c:if>
					<c:if test="${totalPagesSales > 1}">
						<c:if test="${firstPagedPageSales > 2}">
							<a href="${urlBase}?pageSales=1"><b>1</b></a>
							<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
						</c:if>
						<c:forEach begin="${firstPagedPageSales}"
							end="${lastPagedPageSales}" var="i">
							<c:choose>
								<c:when test="${pageSales != i}">
							&nbsp;<a href="${urlBase}?pageSales=${i}"><b>${i}</b></a>&nbsp;
						</c:when>
								<c:otherwise>
								&nbsp;<b>${i}</b>&nbsp;
						</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${lastPagedPageSales < totalPagesSales-1}">
							<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
							<a href="${urlBase}?pageSales=${totalPagesSales}"><b>${totalPagesSales}</b></a>
						</c:if>
					</c:if>
					<!-- A la siguiente pagina -->
					<c:if test="${pageSales < totalPagesSales}">
				&nbsp;&nbsp;<a href="${urlBase}?pageSales=${pageSales + 1}"> <fmt:message
								key="siguiente" bundle="${traducciones}" /></a>
					</c:if>
				</center>
				</p>
			</div>
		</div>
	</div>
</main>
<%@include file="/html/common/footer.jsp"%>