<%@page import="Util.AuthFilter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<style>
    .texto{
        font-family: 'Roboto Slab', serif;
    }
    .texto:hover{
        color: brown;
    }

    .bi-cart4{
        font-size: 18px;
    }

</style>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">

        <a class="logo navbar-brand" href="index.jsp"><img src="Images/logotipo.jpg" width="100px" height="90px"
                                                           alt="img-log"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link texto" aria-current="page" href="index.jsp#hero">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link texto" href="index.jsp#about" >Quiénes Somos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link texto" href="ProductoControlador?accion=categorias">Delivery</a>
                </li>
                <!-- como llegar -->
                <li class="nav-item">
                    <a class="nav-link texto" href="PagCarrito.jsp">Carrito&nbsp;<i class="bi bi-cart4"></i>
                        <span style="color: #009933;font-weight: bold;">(${sessionScope.carrito==null? 0 :   sessionScope.carrito.size()})</span></a>
                </li>
                <c:if test='${sessionScope.usuario == null}'>
                    <li class="nav-item">
                        <a class="nav-link texto" href="PagLogin.jsp">Iniciar Sesión</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link texto" href="PagRegCliente.jsp">Regístrate</a>
                    </li>
                </c:if>
                <c:if test='${AuthFilter.esCliente(sessionScope.usuario)}'>
                    <li class="nav-item">
                        <a class="nav-link texto" href="PedidoControlador?accion=mis_pedidos">Mis Pedidos</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link texto" href="PagContacto.jsp">Contáctenos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link texto" href="https://forms.gle/joLgESLFV1sgczMm8" target="_blank">Encuesta de Satisfacción</a>
                </li>
            </ul>

            <c:if test='${sessionScope.usuario != null}'>
                <div class="btn-group">
                    <button  type="button" class="btn btn-dark dropdown-toggle"  data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
                        ${sessionScope.usuario.username}
                    </button>
                    <ul class="dropdown-menu dropdown-menu-lg-end  text-center">
                        <li><a class="dropdown-item" href="AuthControlador?accion=redirect">MENÚ ${sessionScope.usuario.rol.nombreRol} </a></li>
                        <li><a class="dropdown-item" href="#">
                                <img src="Images/usuario.png" alt="user" width="40"/>
                            </a></li>
                        <li><a class="dropdown-item" href="javascript:void(0)">${sessionScope.usuario.username} </a></li>
                        <div class="dropdown-divider"></div>
                        <li><a class="dropdown-item"  href="javascript:void(0)">${sessionScope.usuario.correo}</a></li>
                        <div class="dropdown-divider"></div>
                        <li>
                            <a href="LoginControlador?accion=logout" class="dropdown-item" >Salir</a>
                        </li>
                    </ul>
                </div>
            </c:if>

        </div>
    </div>
</nav>
