<%@ page import="com.hpr.web.controller.* "%>
<%@include file="/html/common/header.jsp"%>

<div class="registro-form">
	<h1><fmt:message key = "cuenta" bundle="${traducciones}"/></h1>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.EDICION%>">
			<div class="text-box">
			<input type="email" name="<%=ParameterNames.EMAIL%>" placeholder="${sessionScope['user'].email}" readonly/>
			</div>
			<div class="text-box">
			<input type="password" name="<%=ParameterNames.CONTRASENA%>" placeholder="<fmt:message key = "cambiarcontraseña" bundle="${traducciones}"/>"/>
			</div>
			<div class="text-box">
			<label><fmt:message key = "nombre" bundle="${traducciones}"/></label><input type="text" name="<%=ParameterNames.NOMBRE%>" placeholder="${sessionScope['user'].nombre}">
			</div>
			<div class="text-box">
			<label><fmt:message key = "apellidos" bundle="${traducciones}"/></label><input type="text" name="<%=ParameterNames.APELLIDOS%>" placeholder="${sessionScope['user'].apellidos}">
			</div>
			<div class="text-box">
			<label><fmt:message key = "genero" bundle="${traducciones}"/></label><select name="<%=ParameterNames.GENERO%>">
				<option>${sessionScope['user'].genero}</option>
				<option value="null"></option> 
				<option value="M">Masculino</option>
				<option value="F">Femenino</option>
				<option value="O">Otro</option>
			</select>
			</div>
			<div class="text-box">
			<label><fmt:message key = "fechanacimiento" bundle="${traducciones}"/>: ${sessionScope['user'].fechaNacimiento}</label>
			</div>
			<div class="text-box">
			<label><fmt:message key = "telefono" bundle="${traducciones}"/></label><input type="text" name="<%=ParameterNames.TELEFONO%>" placeholder="${sessionScope['user'].telefono}">
			</div>
			<input class="btn" type="submit" value="<fmt:message key = "guardar" bundle="${traducciones}"/>" />
		</form>


</div>

<%@include file="/html/common/footer.jsp"%>