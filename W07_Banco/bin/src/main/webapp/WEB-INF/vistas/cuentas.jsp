<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>cuentas asociadas a ${cli.nombre}</title>
	<link rel="stylesheet" href="${css}/index.css">
	<script>

		function seleccionaCliente(ev){
			var id = ev.currentTarget.id;
			location.href = "${home}/extractos?id="+ id;
		}

// 		window.onload = function(){
// 			var filas = document.getElementsByClassName("filas_clientes");
// 			for (let i = 0; i < filas.length; i++) {
// 				filas[i].addEventListener("click", seleccionaCliente);

// 			}
// 		}
	</script>
</head>
<body>
	<div id="cabecera">
		<h2>cuentas</h2>
	</div>
	<div class="cuerpo">
		<table id="tabla_datos">
			<thead>
				<tr>
					<th colspan=2>cuentas de ${cli.nombre} ${cli.apellido1} ${cli.apellido2}</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="cuenta" items="${cli.cuentas}">
					<tr id="${cli.cuentas}">
						<td>${cuenta}</td>
						<td><a href="${home}/extractos?id=${cli.cuentas}">consultar extracto</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan=2>cantidad cuentas: ${cli.cuentas.size()}</td>
				</tr>
			</tfoot>
		</table>
		<div id="botones">
		<p> <a href="${home}/listado_clientes">volver al listado de clientes</a> </p>
		<p> <a href="${home}/menu_principal">volver al menú principal</a> </p>
		</div>
	</div>
</body>
</html>


<%-- <td><a href="${home}/cuentas?id=${cliente.idCliente}">consultar</a></td> --%>
