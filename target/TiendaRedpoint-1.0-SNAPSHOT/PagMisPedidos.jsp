<%@page import="Util.AuthFilter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet" >
        <link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300;500&display=swap" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css" rel="stylesheet" >
        <title>Red Point</title>
    </head>
    <body>
        
        <jsp:include page="template/Navbar.jsp" />

        <div class="container-fluid mt-3">
            <div class="card">
                <div class="card-body">

                    <span class="badge text-bg-success text-center">Mis Pedidos</span>
                    <hr />
                     <jsp:include page="template/Mensaje.jsp" />
                    <div class="row">
                        <div class="col-lg-12">                 
                            <table class="table table-bordered" id="tabla">
                                <thead class="thead-light">
                                    <tr >
                                        <th class="text-center">Nro</th>                               
                                        <th class="text-center">Fecha</th>
                                        <th class="text-center">Total</th>
                                        <th class="text-center">Entrega</th>
                                        <th class="text-center">Dirección</th>
                                        <th class="text-center">Estado</th>  
                                        <th class="text-center">Acción</th>                       
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${lista}" varStatus="loop"> 
                                        <tr class="text-center">      
                                            <td>${item.nroPed}</td>             
                                            <td>${item.fecha}</td>
                                            <td>${item.montoTotal}</td>  
                                            <td>${item.delivery.hora}</td>
                                            <td>${item.delivery.direccion}</td>
                                            <td>${item.estado}</td>      
                                            <td class="text-center">                                         
                                                <a href="javascript:fnInfoDetalle('${item.nroPed}')" class="btn btn-info btn-sm" title="Ver Detalle">
                                                    <i class="fa fa-info-circle"></i>
                                                </a>
                                                <a href="ReportControlador?accion=pedido&nro=${item.nroPed}" target="_blank"
                                                   class="btn btn-danger btn-sm" title="Descargar PDF">
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
        </div>


        <br />
        <div id="modalDetPedResult"></div>
        <div id="modalEmpResult"></div>
        <jsp:include page="template/Footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/dataTables.bootstrap5.min.js"></script>
    </body>
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
                    "infoEmpty": "Mostrando 0 to 0 of 0 Entradas",
                    "infoFiltered": "(Filtrado de _MAX_ total entradas)",
                    "infoPostFix": "",
                    "thousands": ",",
                    "lengthMenu": "Mostrar _MENU_ Entradas",
                    "loadingRecords": "Cargando...",
                    "processing": "Procesando...",
                    "search": "Buscar:",
                    "zeroRecords": "Sin resultados encontrados",
                    "paginate": {
                        "first": "Primero",
                        "last": "Ultimo",
                        "next": "Siguiente",
                        "previous": "Anterior"
                    }
                }
            });
        });
    </script>
</html>
