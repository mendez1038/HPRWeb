<%@ page import="com.hpr.web.controller.* "%>
<%@include file="/html/common/header.jsp"%>
<main>
<div class="registro-form">
	<div class="registro-form_box">
    <div class="registro-form_titulo">
	
		<fmt:message key="registroform" bundle="${traducciones}" />
	</div>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.REGISTRO%>">
		<div class="text-box">
			<input type="email" name="<%=ParameterNames.EMAIL%>"
				placeholder="<fmt:message key = "email" bundle="${traducciones}" />"
				required />
		</div>
		<div class="text-box">
			<input type="password" name="<%=ParameterNames.CONTRASENA%>"
				placeholder="<fmt:message key = "contrasena" bundle="${traducciones}"/>"
				required />
		</div>
		<div class="text-box">
			<input type="text" name="<%=ParameterNames.NOMBRE%>"
				placeholder="<fmt:message key = "nombre" bundle="${traducciones}"/>">
		</div>
		<div class="text-box">
			<input type="text" name="<%=ParameterNames.APELLIDOS%>"
				placeholder="<fmt:message key = "apellidos" bundle="${traducciones}"/>">
		</div>
		<div class="text-box">
			<label><fmt:message key="genero" bundle="${traducciones}" /></label><select
				name="<%=ParameterNames.GENERO%>">
				<option value="null"></option>
				<option value="M">Masculino</option>
				<option value="F">Femenino</option>
				<option value="O">Otro</option>
			</select>
		</div>

		<label><fmt:message key="fechanacimiento"
				bundle="${traducciones}" /></label><input class="fecha" type="date"
			name="<%=ParameterNames.FECHA_NACIMIENTO%>" required>

		<div class="text-box">
			<input type="text" name="<%=ParameterNames.TELEFONO%>"
				placeholder="<fmt:message key = "telefono" bundle="${traducciones}"/>">
		</div>
		<input class="btn" type="submit"
			value="<fmt:message key = "continuar" bundle="${traducciones}"/>" />
	</form>
	<label><fmt:message key="sicuenta" bundle="${traducciones}" />
	</label> <a href="<%=request.getContextPath() + ViewPaths.LOGIN%>"><fmt:message
			key="iniciosesion" bundle="${traducciones}" /></a>
	</div>
</div>
</main>
<%@include file="/html/common/footer.jsp"%>