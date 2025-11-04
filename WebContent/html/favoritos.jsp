<%@include file="/html/common/header.jsp"%>
<main>
	<div class="tpl-favoritos-hp">
		<div class="tpl-favoritos-hp_max">
			<div class="tpl-favoritos-hp_titulo">
				<fmt:message key="favoritos" bundle="${traducciones}" />
			</div>
			<c:choose>
				<c:when test="${not empty sessionScope['user']}">

					<c:if test="${empty resultados_favoritos}">
						<p>
							<fmt:message key="nofavoritos" bundle="${traducciones}" />
						</p>

					</c:if>
					<div class="tpl-cajas-hp">
						<ul class="sta-cajas-hp_max">
							<c:forEach items="${resultados_favoritos}" var="r">
								<li class="sta-cajas-hp_caja"><a
									href="${pageContext.request.contextPath}/contenido?action=buscar_id&id=${r.idContenido}">
										<div class="sta-cajas-hp_img"
											style="background-image:url('${r.portada}');"></div>
										<div class="sta-cajas-hp_titulos">${r.titulo}</div>
								</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:when>
				<c:otherwise>
					<p>
						<fmt:message key="favoritosnosesion" bundle="${traducciones}" />
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="paginacion">
		<p>
		<center>
			<c:url var="urlBase" value="/favoritos" scope="page"></c:url>

			<!-- Anterior -->
			<c:if test="${pageFavourites > 1}">
				<a href="${urlBase}?pageFavourites=${pageFavourites - 1}"> <fmt:message
						key="anterior" bundle="${traducciones}" />
				</a>&nbsp;&nbsp;
</c:if>

			<c:if test="${totalPagesFavourites > 1}">
				<c:if test="${firstPagedPageFavourites > 2}">
					<a href="${urlBase}?pageFavourites=1"><b>1</b></a>
					<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
				</c:if>

				<c:forEach begin="${firstPagedPageFavourites}"
					end="${lastPagedPageFavourites}" var="i">
					<c:choose>
						<c:when test="${pageFavourites != i}">
        &nbsp;<a href="${urlBase}?pageFavourites=${i}"><b>${i}</b></a>&nbsp;  <!-- FIX del typo -->
						</c:when>
						<c:otherwise>
        &nbsp;<b>${i}</b>&nbsp;
      </c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${lastPagedPageFavourites < totalPagesFavourites - 1}">
					<c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
					<a href="${urlBase}?pageFavourites=${totalPagesFavourites}"><b>${totalPagesFavourites}</b></a>
				</c:if>
			</c:if>

			<!-- Siguiente -->
			<c:if test="${pageFavourites < totalPagesFavourites}">
  &nbsp;&nbsp;<a href="${urlBase}?pageFavourites=${pageFavourites + 1}">
					<fmt:message key="siguiente" bundle="${traducciones}" />
				</a>
			</c:if>

		</center>
		</p>
	</div>
</main>
<%@include file="/html/common/footer.jsp"%>