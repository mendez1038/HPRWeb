<%@ page import="com.hpr.web.controller.* "%>

<%@include file="/html/common/header.jsp"%>

<div id="login-form">
		<h3>Autentícate para obtener tus pelicula favoritas...</h3>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>">
			<input type="email" name="<%=ParameterNames.EMAIL%>" placeholder="Correo lectronico"/>
			<input type="password" name="<%=ParameterNames.CONTRASENA%>" placeholder="Contrasena"/>
			<input type="checkbox" name="<%=ParameterNames.RECORDARME%>"/><label>Recordarme</label>
			<input type="submit" value="Iniciar Sesion" />
		</form>
		<a href="#ayuda">¿Necesitas ayuda?</a>
		<p>¿No tienes cuenta? </p>
		<a href="<%=ControllerPaths.USUARIO%>?action=<%=Actions.PREREGISTRO%>">Crea tu cuenta</a>
		
</div>

<%@include file="/html/common/footer.jsp"%>