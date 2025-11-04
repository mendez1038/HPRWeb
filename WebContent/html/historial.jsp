<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="/html/common/header.jsp"%>

<main>
  <div class="tpl-historial-hp">
    <div class="tpl-historial-hp_max">
      <div class="tpl-historial-hp_titulo">Historial</div>

      <c:choose>
        <c:when test="${empty resultados_historial || totalHistorial == 0}">
          <p><fmt:message key="pedidos.vacio" /></p>
        </c:when>
        <c:otherwise>

          <!-- bucle 1: pedidos -->
          <c:forEach var="p" items="${resultados_historial}" varStatus="st">
            <article class="tpl-historial-hp_pedido">
              <header>
                Pedido #${p.idPedido}
                <span>
                  <fmt:formatDate value="${p.fechaPedido}" pattern="dd/MM/yyyy"/>
                </span>
                <span>
                  <c:choose>
                    <c:when test="${not empty p.lineas}">
                      ${fn:length(p.lineas)} productos
                    </c:when>
                    <c:otherwise> </c:otherwise>
                  </c:choose>
                </span>
              </header>

              <!-- bucle 2: líneas del pedido -->
              <section>
                <c:if test="${not empty p.lineas}">
                  <c:forEach var="l" items="${p.lineas}" varStatus="ls">
                    <!-- muestra las primeras 3 líneas para no alargar demasiado -->
                    <c:if test="${ls.index < 3}">
                      <div class="tpl-historial-hp_first">
                        <!-- No tenemos imagen/título de CONTENIDO; deja placeholder o integra si lo añades -->
                        <span>Contenido #${l.idContenido}</span> Precio: ${l.precioUnidad} €
                      </div>

                 
                    </c:if>
                  </c:forEach>
                </c:if>

                <!-- Si no hay líneas cargadas, muestra un acceso directo al detalle -->
                <c:if test="${empty p.lineas}">
                  <div class="tpl-historial-hp_first">
                    <span>Ver detalles del pedido</span>
                  </div>
                </c:if>
              </section>
              <!-- fin bucle 2 -->

              <footer>
                Precio Total:
                <strong>${p.precioTotal} €</strong>
              </footer>
            </article>
          </c:forEach>
          <!-- fin bucle 1 -->


        </c:otherwise>
      </c:choose>

    </div>
  </div>
</main>

<%@include file="/html/common/footer.jsp"%>
