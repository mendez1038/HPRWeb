<%@ page import="com.hpr.web.controller.* "%>

<%@include file="/html/common/header.jsp"%>

<div id="registro-form">
		<h3>Formulario de registro</h3>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.REGISTRO%>">
			<input type="email" name="<%=ParameterNames.EMAIL%>" placeholder="Email"/>
			<input type="password" name="<%=ParameterNames.CONTRASENA%>" placeholder="Contraseña"/>
			<input type="text" name="<%=ParameterNames.NOMBRE%>" placeholder="Nombre">
			<input type="text" name="<%=ParameterNames.APELLIDOS%>" placeholder="Apellidos">
			<label>Genero</label><select name="<%=ParameterNames.GENERO%>">
				<option value="null"></option> 
				<option value="M">Masculino</option>
				<option value="F">Femenino</option>
				<option value="O">Otro</option>
			</select>
			<label>Fecha Nacimiento</label><input type="text" name="<%=ParameterNames.FECHA_NACIMIENTO%>" placeholder="aaaammdd">
			<input type="text" name="<%=ParameterNames.TELEFONO%>" placeholder="Introduzca su telefono">
			<input type="submit" value="Continuar" />
		</form>
		
		
</div>

<%@include file="/html/common/footer.jsp"%>