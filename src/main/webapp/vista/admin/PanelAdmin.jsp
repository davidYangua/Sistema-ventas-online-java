<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Util.AuthFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Inicio</title>
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body onload="CargarDatos()">

        <c:if test='${!AuthFilter.esAdmin(sessionScope.usuario)}'>
            <c:redirect url="LoginControlador?accion=redirect" />
        </c:if>

        <jsp:include page="../../template/NavbarPro.jsp" />

        <div class="container mt-4">

            <div class="row text-center">
                <div class="col-lg-3 col-md-6 mb-3">
                    <div class="card border-primary shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <i class="fa fa-users fa-2x"></i>
                            <h5 class="mb-0">${cant_clientes}</h5>
                            <p>Clientes Registrados</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 mb-3">
                    <div class="card border-success shadow-sm">
                        <div class="card-header bg-success text-white">
                            <i class="fa fa-shopping-cart fa-2x"></i>
                            <h5 class="mb-0">${cant_pedidos}</h5>
                            <p>Pedidos Realizados</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 mb-3">
                    <div class="card border-warning shadow-sm">
                        <div class="card-header bg-warning text-dark">
                            <i class="fa fa-hourglass-half fa-2x"></i>
                            <h5 class="mb-0">${cant_por_atender}</h5>
                            <p>Pedidos en Espera</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 mb-3">
                    <div class="card border-info shadow-sm">
                        <div class="card-header bg-info text-white">
                            <i class="fa fa-check-circle fa-2x"></i>
                            <h5 class="mb-0">${pedidos_atendidos}</h5>
                            <p>Pedidos Atendidos</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Sección de Gráficos -->
            <div class="card mt-4">
                <div class="card-header bg-primary text-white text-center">
                    <h5>REPORTE DEL AÑO <span id="lbAnnio"></span></h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-6 col-md-12 mb-4">
                            <div id="grafico_recaudacion">Cargando...</div>
                        </div>
                        <div class="col-lg-6 col-md-12 mb-4">
                            <div id="grafico_cliente">Cargando...</div>
                        </div>
                        <div class="col-lg-6 col-md-12 mb-4">
                            <div id="grafico_pedidos">Cargando...</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../../template/Footer.jsp" />
    </body>

    <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    <script src="./js/grafico/highcharts.js"></script>
    <script src="./js/grafico/exporting.js"></script>
    <script src="./js/grafico/export-data.js"></script>
    <script src="./js/grafico/accessibility.js"></script>

    <script type="text/javascript">
        function CargarDatos() {
            var annio = new Date().getFullYear();
            $('#lbAnnio').html(annio);
            ObtenerDatos("recaudacion", "Recaudación en soles del año " + annio, "grafico_recaudacion");
            ObtenerDatos("cliente", "Registros de Clientes del año " + annio, "grafico_cliente");
            ObtenerDatos("pedidos", "Registros de Pedidos del año " + annio, "grafico_pedidos");
        }

        function ObtenerDatos(rol, titulo, cuerpo) {
            $.ajax({
                type: "get",
                dataType: 'json',
                url: "AdminControlador",
                async: false,
                cache: false,
                data: {accion: "reporte", rol: rol},
                success: function (response) {
                    var data = [];
                    for (var i = 0; i < response.length; i++) {
                        data.push({name: response[i].nombreMes, y: response[i].cantidad});
                    }
                    Graficar(data, titulo, cuerpo);
                }
            });
        }

        function Graficar(data, titulo, div) {
            Highcharts.chart(div, {
                chart: {plotBackgroundColor: null, plotBorderWidth: null, plotShadow: false, type: 'pie'},
                title: {text: titulo},
                tooltip: {pointFormat: '{series.name}: <b>{point.y}</b>'},
                accessibility: {point: {valueSuffix: '%'}},
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {enabled: true, format: '<b>{point.name}</b>: {point.percentage:.1f}%'}
                    }
                },
                series: [{name: 'Cantidad', colorByPoint: true, data: data}]
            });
        }
    </script>
</html>
