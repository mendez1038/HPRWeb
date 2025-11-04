<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/html/common/header.jsp" %>

<main>
  <div class="tpl-eliminar-cuenta">
    <div class="tpl-eliminar-cuenta_max">
      <h1 class="tpl-eliminar-cuenta_titulo">Eliminar cuenta</h1>

      <c:if test="${not empty error}">
        <div class="tpl-eliminar-cuenta_error">${error}</div>
      </c:if>

      <form method="post" action="${pageContext.request.contextPath}/eliminarcuenta">
        <input type="hidden" name="action" value="eliminar_cuenta"/>

        <p class="tpl-eliminar-cuenta_texto">
          Esta acción <strong>eliminará permanentemente</strong> tu cuenta y todos tus datos asociados
          (pedidos, favoritos, biblioteca, etc.).  
          Si estás seguro, escribe <strong>ELIMINAR</strong> en el campo de confirmación y tu contraseña actual.
        </p>

        <div class="tpl-eliminar-cuenta_campos">
          <label>Confirmación</label>
          <input type="text" name="confirm" placeholder="ELIMINAR" required />

          <label>Contraseña</label>
          <input type="password" name="contrasena" placeholder="Tu contraseña" required />
        </div>

        <div class="tpl-eliminar-cuenta_botones">
          <button type="submit" class="boton boton-peligro">Eliminar mi cuenta</button>
          <a href="${pageContext.request.contextPath}/index" class="boton boton-secundario">
            Cancelar
          </a>
        </div>
      </form>
    </div>
  </div>
</main>

<%@ include file="/html/common/footer.jsp" %>
