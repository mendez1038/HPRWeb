<%@ page import="com.hpr.web.controller.* "%>
<%@include file="/html/common/header.jsp"%>

<div id="perfil-form">
	<h3>Edición del perfil</h3>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.EDICION%>">
			<input type="password" name="<%=ParameterNames.CONTRASENA%>" placeholder="Contraseña"/>
			<input type="text" name="<%=ParameterNames.NOMBRE%>" placeholder="${sessionScope['user'].nombre}">
			<input type="text" name="<%=ParameterNames.APELLIDOS%>" placeholder="${sessionScope['user'].apellidos}">
			<label>Genero</label><select name="<%=ParameterNames.GENERO%>">
				<option>${sessionScope['user'].genero}</option>
				<option value="null"></option> 
				<option value="M">Masculino</option>
				<option value="F">Femenino</option>
				<option value="O">Otro</option>
			</select>
			<label>Fecha Nacimiento</label><input type="text" name="<%=ParameterNames.FECHA_NACIMIENTO%>" placeholder="${sessionScope['user'].fechaNacimiento}">
			<input type="text" name="<%=ParameterNames.TELEFONO%>" placeholder="${sessionScope['user'].telefono}">
			<input type="submit" value="Continuar" />
		</form>


</div>

<%@include file="/html/common/footer.jsp"%>