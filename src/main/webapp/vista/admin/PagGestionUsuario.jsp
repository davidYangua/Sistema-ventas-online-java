<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Util.AuthFilter"%>
<%@page import="Util.Constantes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Gestión de Comentarios</title>
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css" rel="stylesheet">

        <style>
            .table th, .table td {
                text-align: center;
            }
            .table thead {
                position: sticky;
                top: 0;
                background-color: #343a40;
                color: #fff;
                z-index: 1;
            }
            .table tbody tr:nth-child(odd) {
                background-color: #f8f9fa;
            }
            .table tbody tr:hover {
                background-color: #e9ecef;
            }
            .btn-sm {
                font-size: 0.875rem;
            }
        </style>
    </head>
    <body>
        <c:if test='${!AuthFilter.esAdmin(sessionScope.usuario)}'>
            <c:redirect url="LoginControlador?accion=redirect" />
        </c:if>

        <jsp:include page="../../template/NavbarPro.jsp" />

        <div class="container-fluid mt-5">
            <div class="card shadow-sm border-1">
                <div class="card-header bg-dark text-white d-flex align-items-center justify-content-between">
                    <h4 class="mb-0"><i class="fa fa-users me-2"></i>Gestión de Usuarios</h4>
                </div>
                <div class="card-body">
                    <div class="table-responsive mt-4">
                        <jsp:include page="../../template/Mensaje.jsp" />
                        <table class="table table-bordered table-hover align-middle" id="tabla">
                            <thead>
                                <tr>
                                    <th><i class="fa fa-hashtag"></i> Código Usuario</th>
                                    <th><i class="fa fa-user"></i> Nombre</th>
                                    <th><i class="fa fa-envelope"></i> Correo</th>
                                    <th><i class="fa fa-map-marker-alt"></i> Dirección</th>
                                    <th><i class="fa fa-phone"></i> Teléfono</th>
                                    <th><i class="fa fa-calendar-alt"></i> Fecha de Creación</th>
                                    <th><i class="fa fa-comments"></i> Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${lista}" varStatus="loop">
                                    <tr>
                                        <td>${item.usuario.codUsuario}</td>
                                        <td>${item.usuario.username}</td>
                                        <td>${item.usuario.correo}</td>
                                        <td>${item.direccion}</td>
                                        <td>${item.telefono}</td>
                                        <td>${item.usuario.fecha}</td>
                                        <td>
                                            <span class="badge ${item.usuario.estado == 1 ? 'bg-success' : 'bg-danger'}">
                                                ${item.usuario.estado == 1 ? "Autorizado" : "Denegado"}
                                            </span>
                                            <a 
                                                class="btn btn-sm ${item.usuario.estado == 1 ? 'btn-danger' : 'btn-success'} ms-2"
                                                href="AdminControlador?accion=cambiar_estado&idCliente=${item.idCliente}&nuevoEstado=${(item.usuario.estado == 1 ? 0 : 1)}">
                                                ${item.usuario.estado == 1 ? "Denegar" : "Autorizar"}
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../../template/Footer.jsp" />

        <div id="modalDetPedResult"></div>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/dataTables.bootstrap5.min.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {
                $('#tabla').DataTable({
                    language: {
                        "decimal": "",
                        "emptyTable": "No hay información",
                        "info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
                        "infoEmpty": "Mostrando 0 a 0 de 0 Entradas",
                        "infoFiltered": "(Filtrado de _MAX_ total entradas)",
                        "lengthMenu": "Mostrar _MENU_ Entradas",
                        "loadingRecords": "Cargando...",
                        "processing": "Procesando...",
                        "search": "Buscar:",
                        "zeroRecords": "Sin resultados encontrados",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        }
                    }
                });
            });

        </script>
    </body>
</html>
