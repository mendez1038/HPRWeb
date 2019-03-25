<%@ page import="com.david.training.model.*"%>

<%@include file="/html/common/header.jsp"%>

<div id="home">
	<h1>Registrate para tener a tu disposicion las mejores series y peliculas</h1>

	<div id="iniciosesion">

		<form method="get">
		<button type="submit" formaction="<%=request.getContextPath() + ViewPaths.LOGIN%>">Iniciar Sesion</button> 
		</form>
		<form action="get">
		<button type="submit" formaction="<%=request.getContextPath() + ViewPaths.REGISTRO%>">Registrate</button>
		</form>
	</div>

</div>

<%@include file="/html/common/footer.jsp"%>