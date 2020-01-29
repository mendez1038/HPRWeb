<%@include file="/html/common/header.jsp"%>
<main>
	<div class="tpl-historial-hp">
		<div class="tpl-historial-hp_max">
			<div class="tpl-historial-hp_titulo">Historial</div>
			<!--bucle 1-->
			<article class="tpl-historial-hp_pedido">
				<header>
					Pedido i <span>Fecha</span><span>x productos</span>
				</header>
				<!--bucle 2-->
				<section>
					<a href="" class="tpl-historial-hp_first"> <img
						src="./img/peli.jpg"> <span>Titulo</span>
					</a>
					<div class="tpl-historial-hp_precio">Precio Inicial: 0.0</div>
					<div class="tpl-historial-hp_precioDes">Precio Descontado</div>
					<div class="tpl-historial-hp_precioFin">Precio: 0.0</div>
				</section>
				<!--fin bucle 2-->
				<footer>Precio</footer>
			</article>
			<!--fin bucle 1-->
		</div>
	</div>


</main>
<%@include file="/html/common/footer.jsp"%>