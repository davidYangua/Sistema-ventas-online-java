<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
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
        <style>
            #btnSubir {
                display: none;
                z-index: 1000;
            }
        </style>
        <title>Red Point</title>
    </head>
    <body onload="ListarProd(0)">
        
        <jsp:include page="template/Navbar.jsp" />

        <div class="container-fluid mt-3">
            <div class="row justify-content-md-around">
                <!-- Filtro -->
                <div class="col-lg-2 col-md-2">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Filtrar</h5>
                            <hr>
                            <br />
                            <div>
                                <h6 class="card-subtitle mb-2 text-body-secondary">Categorías</h6>
                                <select class="form-select" id="sel" aria-label="Default select example">
                                    <option value="0">Seleccionar...</option>
                                    <c:forEach var="item" items="${lista}" varStatus="loop">
                                        <option value="${item.idCategoria}">${item.nombreCat}</option>
                                    </c:forEach>
                                    <option selected>Todos</option>
                                </select>
                            </div>
                            <br />
                            <div>
                                <br>
                                <h6 class="card-subtitle mb-2 text-body-secondary">Filtrar por Precio</h6>
                                <div class="mb-3">
                                    <label for="rango" class="form-label">S/. <span id="rangeValue">150</span> Soles</label>
                                    <input type="range" class="form-range" min="0" max="300" step="1" id="rango" value="150">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Productos -->
                <div class="col-lg-9 col-md-9 ms-lg-3" style="margin-right: 40px;"> <!-- Margen izquierdo ajustado a 15px en pantallas grandes -->
                    <div class="card">
                        <div class="card-body">
                            <div class="text-center">
                                <span class="badge text-bg-info">Delivery</span>
                                <hr />
                            </div>
                            <form class="row g-3">
                                <div class="col-auto">
                                    <label class="col-form-label">Buscar:</label>
                                </div>
                                <div class="col-auto">
                                    <input type="search" class="form-control" name="filtro" id="filtro">
                                </div>
                            </form>
                            <div id="productoList"></div> <!-- Aquí se cargarán los productos -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <br />

        <jsp:include page="template/Footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        
        <button id="btnSubir" class="btn btn-primary position-fixed bottom-0 end-0 m-4" onclick="subirPagina()">
            <i class="bi bi-arrow-up"></i>
        </button>
    </body>


    <script>
            $("#filtro").keyup(function () {
                ListarProd(0);
            });

            function ListarProd(range) {
                if (range === 0) {
                    var filtro = $('#filtro').val();
                    var categ = $('#sel').val() === 'Todos' ? 0 : $('#sel').val();
                    $("#productoList").html("Cargando...");
                    $("#productoList").load("ProductoControlador?accion=filtro_delivery&filtro=" + filtro + "&categ=" + categ + '&rango=' + range);
                } else {
                    var filtro = $('#filtro').val();
                    var categ = $('#sel').val() === 'Todos' ? 0 : $('#sel').val();
                    $("#productoList").html("Cargando...");
                    $("#productoList").load("ProductoControlador?accion=filtro_delivery&filtro=" + filtro + "&categ=" + categ + '&rango=' + range);
                }

            }

            const rangeInput = document.querySelector('#rango');
            const rangeValue = document.querySelector('#rangeValue');

            // Actualiza el valor al cargar la página
            //rangeValue.textContent = rangeInput.value;
            // Actualiza el valor mientras el slider se mueve
            rangeInput.addEventListener('input', function (e) {
                rangeValue.textContent = this.value;
                ListarProd(this.value);
            });

            document.querySelector('#sel').addEventListener('change', function (e) {
                //var categ = $('#sel').val() === 'Seleccionar...' ? 0 : $('#sel').val();
                //alert('Ha seleccionado: '+categ)
                var range = document.querySelector('#rango').value;
                if(range !== 0 || range !== 150) {
                    ListarProd(range);
                } else {
                    ListarProd(0);
                }
                
            });
            
            window.onscroll = function () {
                const btnSubir = document.getElementById("btnSubir");
                if (document.documentElement.scrollTop > 100) {
                    btnSubir.style.display = "block";
                } else {
                    btnSubir.style.display = "none";
                }
            };

            function subirPagina() {
                window.scrollTo({top: 0, behavior: 'smooth'});
            }
    </script>
</html>