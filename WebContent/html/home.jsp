<%@ page import="com.hpr.web.controller.* "%>

<%@include file="/html/common/header.jsp"%>

<div id="home">
		<h3><fmt:message key = "bienvenida" bundle="${traducciones}"/></h3>	
		
		<h2><fmt:message key = "novedades" bundle="${traducciones}"/></h2>
		<h2><fmt:message key = "masvendidos" bundle="${traducciones}"/></h2>
		<h2><fmt:message key = "rebajas" bundle="${traducciones}"/></h2>
		
</div>

<%@include file="/html/common/footer.jsp"%>