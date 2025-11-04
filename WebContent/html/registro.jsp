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
						placeholder="<fmt:message key = "email" bundle="${traducciones}" />" autocomplete="email"
						required />
				</div>
				<div class="text-box">
					<input type="password" name="<%=ParameterNames.CONTRASENA%>"
						placeholder="<fmt:message key = "contrasena" bundle="${traducciones}"/>" autocomplete="current-password"
						required />
				</div>
				<div class="text-box">
					<input type="text" name="<%=ParameterNames.NOMBRE%>"
						placeholder="<fmt:message key = "nombre" bundle="${traducciones}"/>" autocomplete="given-name" required> 
				</div>
				<div class="text-box">
					<input type="text" name="<%=ParameterNames.APELLIDOS%>"
						placeholder="<fmt:message key = "apellidos" bundle="${traducciones}"/>" autocomplete="family-name" required>
				</div>
				<div class="text-box">
					<label><fmt:message key="genero" bundle="${traducciones}" /><select
						name="<%=ParameterNames.GENERO%>" required>
						<option value=""></option>
						<option value="M">Masculino</option>
						<option value="F">Femenino</option>
						<option value="O">Otro</option>
					</select></label>
				</div>
				<div class="text-box">
					<label><fmt:message key="fechanacimiento"
							bundle="${traducciones}" /><input class="fecha" type="date"
						name="<%=ParameterNames.FECHA_NACIMIENTO%>" autocomplete="bday" required></label>
				</div>
				<div class="text-box">
					<input type="text" name="<%=ParameterNames.TELEFONO%>"
						placeholder="<fmt:message key = "telefono" bundle="${traducciones}"/>" autocomplete="mobile" required>
				</div>
				<input class="btn" type="submit"
					value="<fmt:message key = "continuar" bundle="${traducciones}"/>" />
			</form>
			<span><fmt:message key="sicuenta" bundle="${traducciones}" />
			</span> <a href="<%=request.getContextPath() + ViewPaths.LOGIN%>"><fmt:message
					key="iniciosesion" bundle="${traducciones}" /></a>
		</div>
	</div>
</main>
<%@include file="/html/common/footer.jsp"%>