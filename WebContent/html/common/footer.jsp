<%@ page
	import="com.hpr.web.controller.*, com.hpr.web.model.*, com.david.training.model.*, com.hpr.web.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
			<fmt:setBundle basename="resources.Messages" var="traducciones" scope="session" />
			<fmt:setLocale value="${sessionScope['user-locale']}" scope="session" />
			<div class="modulo-footer">
				<div class="modulo-footer_max">
				<div class="idioma">
					<p><fmt:message key="idioma" bundle="${traducciones}"/></p>
					[<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.CAMBIAR_IDIOMA%>&amp;<%=ParameterNames.LOCALE%>=<%=ParameterNames.ES%>">ES</a>
					|<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.CAMBIAR_IDIOMA%>&amp;<%=ParameterNames.LOCALE%>=<%=ParameterNames.EN%>">EN</a>]
				</div>
				<ul class="contacto">
					<li><a href="#"><fmt:message key="quiensomos" bundle="${traducciones}"/></a></li>
					<li><a href="javascript:void(0)" class="myBtn"><fmt:message key="contacto" bundle="${traducciones}"/></a></li>
					<div  class="modal">
					  <div class="modal-content">
					    <span class="close">&times;</span>
					    <div class="titulo"><fmt:message key="direccion" bundle="${traducciones}"/></div>
					    <div class="content">
					    	<fmt:message key="calle" bundle="${traducciones}"/>,   nº1,   1º A <br> Chantada - Lugo 27500 <br><br>
      						<a href="tel:+34 900 20 30 40">+34 900 20 30 40</a> <br>
      						<a href="mailto:dmendez1038@gmail.com">info@hprweb.com</a>
					    </div>
					    <div class="titulo"><fmt:message key="atCliente" bundle="${traducciones}"/></div>
					    <div class="content">
					    <fmt:message key="diasSemana" bundle="${traducciones}"/><br><br>
					      9:00 - 14:00  <br>
					      15:00 - 18:00
					    </div>
					  </div>
					
					</div>
				</ul>
				<ul class="politicas">
					<li><a href="<%=request.getContextPath()%>/docs/cookies.pdf" target="_blank"><fmt:message key="cookies" bundle="${traducciones}"/></a></li>
					<li><a href="<%=request.getContextPath()%>/docs/politica_privacidad.pdf" target="_blank"><fmt:message key="politicas" bundle="${traducciones}"/></a></li>
				</ul>
				<div class="copyright">
					<p>©2019 HPR Web, Inc. All Rights Reserved.</p>
				</div>
				</div>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/js/main.js"></script>
		
	</body>
</html>