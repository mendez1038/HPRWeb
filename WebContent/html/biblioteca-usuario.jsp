<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/html/common/header.jsp"%>

<main>
  <div class="tpl-biblioteca-hp">
    <div class="tpl-biblioteca-hp_max">
      <div class="tpl-biblioteca-hp_titulo">
        <fmt:message key="lista" bundle="${traducciones}" />
      </div>

      <c:choose>
        <c:when test="${empty sessionScope['user']}">
          <p><fmt:message key="biblioteca.nosesion" bundle="${traducciones}" /></p>
        </c:when>

        <c:otherwise>
          <c:if test="${empty resultados_biblioteca}">
            <p><fmt:message key="biblioteca.vacia" bundle="${traducciones}" /></p>
          </c:if>

          <c:if test="${not empty resultados_biblioteca}">
            <div class="tpl-cajas-hp">
              <ul class="sta-cajas-hp_max">
                <c:forEach var="c" items="${resultados_biblioteca}">
                  <li class="sta-cajas-hp_caja">
                    <a href="${pageContext.request.contextPath}/contenido?action=buscar_id&id=${c.idContenido}">
                      <div class="sta-cajas-hp_img"
                           style="background-image:url('${c.portada}');"></div>
                      <div class="sta-cajas-hp_titulos ">${c.titulo}</div>
                    </a>
                  </li>
                </c:forEach>
              </ul>
            </div>
          </c:if>
        </c:otherwise>
      </c:choose>
    </div>
  </div>

  <!-- Paginación (estilo Favoritos) -->
  <div class="paginacion">
    <center>
      <c:url var="urlBase" value="/private/biblioteca" scope="page"></c:url>

      <!-- Anterior -->
      <c:if test="${pageBiblioteca > 1}">
        <a href="${urlBase}?pageBiblioteca=${pageBiblioteca - 1}">
          <fmt:message key="anterior" bundle="${traducciones}" />
        </a>&nbsp;&nbsp;
      </c:if>

      <c:if test="${totalPagesBiblioteca > 1}">
        <c:if test="${firstPagedPageBiblioteca > 2}">
          <a href="${urlBase}?pageBiblioteca=1"><b>1</b></a>
          <c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
        </c:if>

        <c:forEach begin="${firstPagedPageBiblioteca}"
                   end="${lastPagedPageBiblioteca}" var="i">
          <c:choose>
            <c:when test="${pageBiblioteca != i}">
              &nbsp;<a href="${urlBase}?pageBiblioteca=${i}"><b>${i}</b></a>&nbsp;
            </c:when>
            <c:otherwise>
              &nbsp;<b>${i}</b>&nbsp;
            </c:otherwise>
          </c:choose>
        </c:forEach>

        <c:if test="${lastPagedPageBiblioteca < totalPagesBiblioteca - 1}">
          <c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
          <a href="${urlBase}?pageBiblioteca=${totalPagesBiblioteca}">
            <b>${totalPagesBiblioteca}</b>
          </a>
        </c:if>
      </c:if>

      <!-- Siguiente -->
      <c:if test="${pageBiblioteca < totalPagesBiblioteca}">
        &nbsp;&nbsp;<a href="${urlBase}?pageBiblioteca=${pageBiblioteca + 1}">
          <fmt:message key="siguiente" bundle="${traducciones}" />
        </a>
      </c:if>
    </center>
  </div>
</main>

<%@include file="/html/common/footer.jsp"%>
