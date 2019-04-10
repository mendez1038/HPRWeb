<%@ page import="com.hpr.web.controller.*, com.hpr.web.model.*, com.david.training.model.*, com.hpr.web.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources.Messages" var="traducciones" scope="session"/>
<fmt:setLocale value="${sessionScope['user-locale']}" scope="session"/>

	<div id="footer">
		<p>©2019 HPR Web, Inc. All Rights Reserved.</p> 
		<p><fmt:message key = "idioma" bundle="${traducciones}"/></p>
		[<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.CAMBIAR_IDIOMA%>&amp;<%=ParameterNames.LOCALE%>=<%=ParameterNames.ES%>">ES</a>
		|<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.CAMBIAR_IDIOMA%>&amp;<%=ParameterNames.LOCALE%>=<%=ParameterNames.EN%>">EN</a>]
		
	</div>
	</body>
</html>