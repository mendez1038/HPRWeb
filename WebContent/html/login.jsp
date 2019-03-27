<%@ page import="com.hpr.web.controller.* "%>
<%-- <%@ page import="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" %>
 --%>
<%@include file="/html/common/header.jsp"%>

<div class="login-form">
		<h1>Iniciar Sesión</h1>
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>">
			<div class="text-box">
			<!-- <i class="fa fa-user" aria-hidden="true"></i>	
			 --><input type="email" name="<%=ParameterNames.EMAIL%>" placeholder="Correo electrónico"/>
			</div>
			<div class="text-box">
			<!-- <i class="fa fa-user" aria-hidden="true"></i>
			 --><input type="password" name="<%=ParameterNames.CONTRASENA%>" placeholder="Contraseña"/>
			</div>
			
			<input class="btn" type="submit" value="Iniciar Sesion"/>
		</form>
		<input type="checkbox" name="<%=ParameterNames.RECORDARME%>" /><label>Recordarme</label>
		<a href="#ayuda">¿Necesitas ayuda?</a><br><br>
		<label>¿No tienes cuenta? </label>
		<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.PREREGISTRO%>">Crea tu cuenta</a>
</div>		


<%@include file="/html/common/footer.jsp"%>