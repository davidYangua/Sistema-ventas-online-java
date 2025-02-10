<%@page import="Util.Constantes"%>
<%@page import="Modelo.Usuario"%>
<%@page import="Util.AuthFilter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test='${AuthFilter.esAdmin(sessionScope.usuario)}'>
    <nav class="navbar navbar-expand-lg bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" style="color: white;">Red Point</a>
            <button class="navbar-toggler" style="color: white;" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent" style="justify-content: space-between">
                <div class="navbar-nav">
                    <a style="margin-left: 10px; border: nome;" class="btn btn-outline-light" aria-current="page" href="${pageContext.request.contextPath}/AdminControlador?accion=inicio">Dashboard</a>
                    <a style="margin-left: 10px; border: nome;" class="btn btn-outline-light" href="${pageContext.request.contextPath}/AdminControlador?accion=productos">Productos</a>
                    <a style="margin-left: 10px; border: nome;" class="btn btn-outline-light" href="${pageContext.request.contextPath}/AdminControlador?accion=gestion_usuarios">Gestión de Clientes</a>
                    <a style="margin-left: 10px;" class="btn btn-outline-light position-relative px-2 border border-white" href="${pageContext.request.contextPath}/AdminControlador?accion=pedidos">
                        Pedidos
                        <span id="pedido-count" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                            ${cant_por_atender}
                        </span>
                    </a>
                        <a style="margin-left: 10px; border: nome;" class="btn btn-outline-light" href="${pageContext.request.contextPath}/AdminControlador?accion=comentarios">Comentarios</a>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-outline-light dropdown-toggle"  data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
                        ${usuario.getUsername()}
                    </button>
                    <ul class="dropdown-menu dropdown-menu-lg-end  text-center">
                        <li><a class="dropdown-item" href="LoginControlador?accion=redirect">MENÚ ${sessionScope.usuario.rol.nombreRol} </a></li>
                        <li><a class="dropdown-item" href="#">
                                <img src="${pageContext.request.contextPath}/Images/usuario.png" alt="user" width="40"/>
                            </a></li>
                        <li><a class="dropdown-item" href="javascript:void(0)">${usuario.getUsername()} </a></li>
                        <div class="dropdown-divider"></div>
                        <li><a class="dropdown-item"  href="javascript:void(0)">${usuario.getCorreo()}</a></li>
                        <div class="dropdown-divider"></div>
                        <li>
                            <a href="${pageContext.request.contextPath}/LoginControlador?accion=logout" class="dropdown-item" >Salir</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</c:if>
<script>

    $(document).ready(function () {
        // Recupera el número de pedidos desde sessionStorage al cargar la página
        const storedPedidos = sessionStorage.getItem('nuevosPedidos');
        if (storedPedidos) {
            actualizarContadorPedidos(parseInt(storedPedidos));
        }

        // Llama a la función inmediatamente y luego en intervalos
        obtenerNumeroDePedidos();
        setInterval(obtenerNumeroDePedidos, 10000);
    });

    function obtenerNumeroDePedidos() {
        $.ajax({
            type: "get",
            dataType: 'json',
            url: "${pageContext.request.contextPath}/AdminControlador",
            data: {
                accion: "pedidos_en_espera"
            },
            success: function (response) {
                console.log("Respuesta del servidor:", response);

                // Verifica que la respuesta es válida, o mantén el valor actual
                const nuevosPedidos = response !== undefined ? response : $('#pedido-count').text();
                actualizarContadorPedidos(nuevosPedidos);

                // Almacena el número actual de pedidos en sessionStorage
                sessionStorage.setItem('nuevosPedidos', nuevosPedidos);
            },
            error: function () {
                console.error('Error al obtener el número de pedidos.');
                // En caso de error, mantener el número actual sin resetear
            }
        });
    }

// Función para actualizar el contador de pedidos
    function actualizarContadorPedidos(nuevosPedidos) {
        const pedidoCountElement = $('#pedido-count');
        pedidoCountElement.text(nuevosPedidos);

        if (nuevosPedidos > 0) {
            pedidoCountElement.removeClass('d-none'); // Muestra el contador si hay nuevos pedidos
        } else {
            pedidoCountElement.addClass('d-none'); // Oculta si no hay nuevos pedidos
        }
    }

</script>