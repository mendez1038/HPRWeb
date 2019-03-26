<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.hpr.web.controller.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Not Found!!!</title>
</head>
<body>
	<h3>Error 404</h3>
	<img src="<%=request.getContextPath()%>/imgs/logo.png"></img>
	<a href="<%=request.getContextPath()%>">Inicio</a>
	<a href="<%=request.getContextPath()+ViewPaths.HOME%>">Home</a>
</body>
</html>