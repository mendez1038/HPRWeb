<%@ page import="com.hpr.web.controller.* "%>

<%@include file="/html/common/header.jsp"%>

<div id="registro-form">
		<h3><fmt:message key = "registroform" bundle="${traducciones}"/></h3>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.REGISTRO%>">
			<input type="email" name="<%=ParameterNames.EMAIL%>" placeholder="<fmt:message key = "email" bundle="${traducciones}" />" required/>
			<input type="password" name="<%=ParameterNames.CONTRASENA%>" placeholder="<fmt:message key = "contrasena" bundle="${traducciones}"/>" required/>
			<input type="text" name="<%=ParameterNames.NOMBRE%>" placeholder="<fmt:message key = "nombre" bundle="${traducciones}"/>">
			<input type="text" name="<%=ParameterNames.APELLIDOS%>" placeholder="<fmt:message key = "apellidos" bundle="${traducciones}"/>">
			<label><fmt:message key = "genero" bundle="${traducciones}"/></label><select name="<%=ParameterNames.GENERO%>">
				<option value="null"></option> 
				<option value="M">Masculino</option>
				<option value="F">Femenino</option>
				<option value="O">Otro</option>
			</select>
			<label><fmt:message key = "fechanacimiento" bundle="${traducciones}"/></label><input type="date" name="<%=ParameterNames.FECHA_NACIMIENTO%>" required>
			<input type="text" name="<%=ParameterNames.TELEFONO%>" placeholder="<fmt:message key = "telefono" bundle="${traducciones}"/>">
			<input type="submit" value="<fmt:message key = "continuar" bundle="${traducciones}"/>" />
		</form>
		<label><fmt:message key = "sicuenta" bundle="${traducciones}"/> </label>
		<a href="<%=request.getContextPath() + ViewPaths.LOGIN%>"><fmt:message key = "iniciosesion" bundle="${traducciones}"/></a>
		
</div>

<%@include file="/html/common/footer.jsp"%>