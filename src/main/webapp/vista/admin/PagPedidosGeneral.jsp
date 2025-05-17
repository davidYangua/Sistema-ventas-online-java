<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Util.AuthFilter"%>
<%@page import="Util.Constantes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Mis Pedidos</title>
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css" rel="stylesheet">

        <style>
            /* Estilos personalizados */
            .table thead {
                background-color: #343a40;
                color: #fff;
            }

            .table tbody tr:nth-child(odd) {
                background-color: #f8f9fa;
            }

            .table tbody tr:hover {
                background-color: #e9ecef;
            }

            .badge-status {
                padding: 0.4em 0.6em;
                font-size: 0.9em;
                color: #fff;
                border-radius: 0.25em;
            }

            .badge-en-proceso {
                background-color: #ffc107;
            }

            .badge-atendido {
                background-color: #28a745;
            }

            .btn-action {
                margin-right: 5px;
            }

            .table th, .table td {
                vertical-align: middle;
                text-align: center;
            }

            /* Iconos en encabezados */
            .table th i {
                margin-right: 5px;
            }
        </style>
    </head>
    <body>
        <c:if test='${!AuthFilter.esAdmin(sessionScope.usuario)}'>
            <c:redirect url="LoginControlador?accion=redirect" />
        </c:if>

        <jsp:include page="../../template/NavbarPro.jsp" />

        <div class="container-fluid mt-3">
            <div class="card">
                <div class="card-body">
                    <div class="card-header bg-dark text-white d-flex align-items-center justify-content-between">
                        <h4 class="mb-0"><i class="bi bi-box2-fill"></i> Gestión de Pedidos</h4>
                    </div>
                    <hr>

                    <div class="table-responsive mt-4">
                        <jsp:include page="../../template/Mensaje.jsp" />
                        <table class="table table-bordered table-hover" id="tabla">
                            <thead class="thead-dark">
                                <tr>
                                    <th><i class="fa fa-hashtag"></i> Nro</th>       
                                    <th><i class="fa fa-user"></i> Cliente</th>
                                    <th><i class="fa fa-phone"></i> Teléfono</th>
                                    <th><i class="fa fa-calendar"></i> Fecha</th>
                                    <th><i class="fa fa-clock"></i> Entrega</th>
                                    <th><i class="fa fa-map-marker-alt"></i> Dirección</th>
                                    <th><i class="fa fa-info-circle"></i> Estado</th>
                                    <th><i class="fa fa-cog"></i> Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${lista}" varStatus="loop"> 
                                    <tr>      
                                        <td>${item.nroPed}</td>             
                                        <td>${item.cliente.nombres} ${item.cliente.apellidos}</td>  
                                        <td>${item.cliente.telefono}</td>   
                                        <td>${item.fecha}</td>
                                        <td>${item.delivery.hora}</td>
                                        <td>${item.delivery.direccion}</td>
                                        <td>
                                            <span class="badge badge-status
                                                  ${item.cantDetalles > 0 ? 'badge-en-proceso' : 'badge-atendido'}">
                                                ${item.cantDetalles > 0 ? 'En Proceso' : 'Atendido'}
                                            </span>
                                        </td>
                                        <td>
                                            <a href="javascript:fnInfoDetalle('${item.nroPed}')" 
                                               class="btn btn-info btn-sm btn-action" title="Ver Detalle">
                                                <i class="fa fa-info-circle"></i>
                                            </a>
                                            <c:if test='${item.cantDetalles > 0}'>
                                                <a href="AdminControlador?accion=atender_pedido&nro=${item.nroPed}" 
                                                   class="btn btn-primary btn-sm btn-action" title="Atender">
                                                    <i class="fa fa-paper-plane"></i>
                                                </a>
                                            </c:if>
                                            <a href="ReportControlador?accion=pedido&nro=${item.nroPed}"
                                               target="_blank" class="btn btn-danger btn-sm btn-action" title="Descargar PDF">
                                                <i class="fa fa-file-pdf"></i>
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
            function fnInfoDetalle(nroPed) {
                $.ajax({
                    type: "get",
                    url: "PedidoControlador",
                    data: {
                        accion: "detalle",
                        nro: nroPed
                    },
                    success: function (data) {
                        $('#modalDetPedResult').html(data);
                        $('#modalInfoDetallePed').modal('show');
                    }
                });
            }

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
