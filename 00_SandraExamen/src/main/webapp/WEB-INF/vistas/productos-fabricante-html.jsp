<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>listado productos</title>
<link rel="stylesheet" href="${css}/index.css">
<script>

	function solicitud(){
		// preparamos los parámetros de la petición. Ejemplo: idFabricante=17
		var params = "idFabricante=" + encodeURIComponent(document.getElementById("idFabricante").value);

		// creamos req, objeto q nos permitirá hacer la petición
		var req = new XMLHttpRequest();

		// definimos el método y la URL
		req.open("post", "productos_fabricante_html_respuesta");

		req.onreadystatechange = function(){
			
		};
	}

	window.onload = function() {
		document.getElementById("idFabricante").addEventListener("change", solicitud);
	}
</script>
</head>
<body>
	<header class="cabecera">
		<nav>
			<li><a href="${home}/listado_productos">listado de productos</a></li>
				<li><a href="${home}/registro_fabricante">alta nuevo fabricante</a></li>
				<li><a href="${home}/registro_producto">agregar nuevo producto</a></li>
				<li><a href="${home}/productos_fabricante">productos por fabricante</a></li>
				<li><a href="${home}/ofertas">ofertones</a></li>
		</nav>
		<hr>
		<h2>búsqueda de productos por fabricante</h2>
	</header>
	<main class="cuerpo">
		<form id="buscar-prod" action="${home}/productos_fabricante"
			method="post">
			<select id="idFabricante" name="idFabricante">
				<!-- <option value="1">fabricante1</option> -->
				<!-- <option value="2">fabricante2</option> -->
				<!-- <option value="3">fabricante3</option> -->
				<!-- <option value="4">fabricante4</option> -->
				<c:if test="${empty fab}">
					<option></option>
				</c:if>
				<!-- la primera opción vacía, a no ser q elija una -->
				<c:forEach var="fabricante" items="${fabs}">
					<option value="${fabricante.idFabricante}"
						${fabricante.idFabricante==fab.idFabricante ? "selected='selected'" : ""}>${fabricante.fabricante}</option>
				</c:forEach>
			</select>
		</form>

		<div id="tabla-clientes">
			<c:if test="${not empty fab}">

				<table id="tabla_datos">
					<thead>
						<tr>
							<th>descripcion</th>
							<th>precio</th>
							<th>fabricante</th>
						</tr>
					</thead>
					<tbody>
						<!-- <c:forEach var="producto" items="${fab.productos}">
							<tr id="${producto.idProducto}">
								<td>${producto.producto}</td>
								<td>${producto.precio}</td>
								<td>${producto.fabricante}</td>
							</tr>
						</c:forEach> -->
					</tbody>
					<tfoot>
						<tr>
							<td colspan=7>cantidad productos: ${productos.size()}</td>
						</tr>
					</tfoot>
				</table>
			</c:if>
		</div>
	</main>
</body>
</html>
