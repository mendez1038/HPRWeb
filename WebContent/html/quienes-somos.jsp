<%@ page import="com.hpr.web.controller.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/html/common/header.jsp"%>
<main>
<div class="quienes-somos">
<div class="max">
	<h1><fmt:message key="quiensomos" bundle="${traducciones}"/></h1><br><br><br>
	
	<p><fmt:message key="quiensomos_texto1" bundle="${traducciones}"/></p><br><br><br>
	<h1><fmt:message key="quiensomos_texto2" bundle="${traducciones}"/></h1><br><br><br>
	<p><fmt:message key="quiensomos_texto3" bundle="${traducciones}"/></p><br><br>
	<p><fmt:message key="quiensomos_texto4" bundle="${traducciones}"/></p><br><br>
	<p><fmt:message key="quiensomos_texto5" bundle="${traducciones}"/><br><br>
		<fmt:message key="quiensomos_texto6" bundle="${traducciones}"/>
	</p><br><br>
	<p><fmt:message key="quiensomos_texto7" bundle="${traducciones}"/><br><br>
 		<fmt:message key="quiensomos_texto8" bundle="${traducciones}"/>
	</p>
</div>
</div>
</main>
<%@include file="/html/common/footer.jsp"%>