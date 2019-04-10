<%@ page import="com.hpr.web.controller.* "%>
<%-- <%@ page import="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" %>
 --%>
<%@include file="/html/common/header.jsp"%>

<div class="login-form">
		<h1><fmt:message key = "iniciosesion" bundle="${traducciones}"/></h1>
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>">
			<div class="text-box">
			<!-- <i class="fa fa-user" aria-hidden="true"></i>	
			 --><input type="email" name="<%=ParameterNames.EMAIL%>" placeholder="<fmt:message key = "email" bundle="${traducciones}"/>"/>
			</div>
			<div class="text-box">
			<!-- <i class="fa fa-user" aria-hidden="true"></i>
			 --><input type="password" name="<%=ParameterNames.CONTRASENA%>" placeholder="<fmt:message key = "contrasena" bundle="${traducciones}"/>"/>
			</div>
			
			<input class="btn" type="submit" value="<fmt:message key = "iniciosesion" bundle="${traducciones}"/>"/>
		</form>
		<input type="checkbox" name="<%=ParameterNames.RECORDARME%>" /><label><fmt:message key = "recordar" bundle="${traducciones}"/></label>
		<a href="#ayuda"><fmt:message key = "help" bundle="${traducciones}"/></a><br><br>
		<label><fmt:message key = "nocuenta" bundle="${traducciones}"/></label>
		<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.PREREGISTRO%>"><fmt:message key = "registro" bundle="${traducciones}"/></a>
</div>		


<%@include file="/html/common/footer.jsp"%>