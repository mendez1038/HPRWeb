<%@ page
	import="com.hpr.web.controller.*, com.david.training.service.*, com.david.training.model.*,java.util.List"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/html/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<main>
    <div class="modulo-home">
        <div class="modulo-home_max">

            <div class="tpl-title-hp">
                <div class="sta-title-hp_max">
                    <div class="sta-title-hp_titulo">
                        Resultados de la búsqueda: ${total_busqueda}</p>
                    </div>
                </div>
            </div>

            <% 
                List<Contenido> resultadosBusqueda =
                    (List<Contenido>) request.getAttribute(AttributeNames.RESULTADOS_BUSQUEDA);
                if (resultadosBusqueda == null || resultadosBusqueda.isEmpty()) {
            %>
                <div class="tpl-cajas-hp">
                    <p style="text-align: center; padding: 2rem 0; color:#fff;">
                        No se han encontrado contenidos para tu búsqueda.
                    </p>
                </div>
            <%
                } else {
            %>

            <div class="tpl-cajas-hp">
                <ul class="sta-cajas-hp_max">
                    <%
                        for (Contenido resultado : resultadosBusqueda) {
                    %>
                        <li class="sta-cajas-hp_caja">
                            <a title="<%=resultado.getTitulo()%>"
                               href="<%=ControllerPaths.CONTENIDO%>?<%=ParameterNames.ACTION%>=<%=Actions.BUSCAR_ID%>&amp;<%=ParameterNames.ID%>=<%=resultado.getIdContenido()%>">

                                <div class="sta-cajas-hp_img"
                                     style="background-image: url('<%=resultado.getPortada()%>');"></div>

                                <%
                                    if (resultado.getPrecioDescontado() > 0) {
                                        double nuevoPrecio = resultado.getPrecio() - resultado.getPrecioDescontado();
                                %>
                                    <div class="sta-cajas-hp_titulo">
                                        <%=resultado.getTitulo()%>
                                        <div class="sta-cajas-hp_precio">
                                            <span class="sta-nuevo sta-descuento"><%=nuevoPrecio%>€</span>
                                        </div>
                                    </div>
                                <%
                                    } else {
                                %>
                                    <div class="sta-cajas-hp_titulo">
                                        <%=resultado.getTitulo()%>
                                        <div class="sta-cajas-hp_precio">
                                            <span class="sta-nuevo"></span>
                                            <span class="sta-porcentaje"></span>
                                            <span class="sta-precio"><%=resultado.getPrecio()%> €</span>
                                        </div>
                                    </div>
                                <%
                                    }
                                %>
                            </a>
                        </li>
                    <%
                        } // fin for
                    %>
                </ul>
            </div>

            <!-- paginación igual que en la home pero usando /contenido -->
            <div class="paginacion">
                <p>
                    <center>
                        <c:url var="urlBase" value="/contenido" scope="page"></c:url>

                        <c:if test="${page > 1}">
                            <a href="${urlBase}?action=buscar${url}&page_busqueda=${page - 1}">
                                <fmt:message key="anterior" bundle="${traducciones}" />
                            </a>&nbsp;&nbsp;
                        </c:if>

                        <c:if test="${totalPages > 1}">
                            <c:if test="${firstPagedPage > 2}">
                                <a href="${urlBase}?action=buscar${url}&page_busqueda=1"><b>1</b></a>
                                <c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
                            </c:if>

                            <c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
                                <c:choose>
                                    <c:when test="${page != i}">
                                        &nbsp;<a href="${urlBase}?action=buscar${url}&page_busqueda=${i}"><b>${i}</b></a>&nbsp;
                                    </c:when>
                                    <c:otherwise>
                                        &nbsp;<b>${i}</b>&nbsp;
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${lastPagedPage < totalPages-1}">
                                <c>&nbsp;.&nbsp;.&nbsp;.&nbsp;</c>
                                <a href="${urlBase}?action=buscar${url}&page_busqueda=${totalPages}">
                                    <b>${totalPages}</b>
                                </a>
                            </c:if>
                        </c:if>

                        <c:if test="${page < totalPages}">
                            &nbsp;&nbsp;<a href="${urlBase}?action=buscar${url}&page_busqueda=${page + 1}">
                                <fmt:message key="siguiente" bundle="${traducciones}" />
                            </a>
                        </c:if>
                    </center>
                </p>
            </div>

            <%
                } // fin else
            %>

        </div>
    </div>
</main>

<%@include file="/html/common/footer.jsp"%>