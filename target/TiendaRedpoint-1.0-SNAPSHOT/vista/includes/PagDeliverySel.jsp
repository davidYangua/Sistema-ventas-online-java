<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet">
        <style>
            .no-results {
                text-align: center;
                font-size: 1.5em;
                color: #888;
                margin-top: 50px;
                animation: fadeIn 1.5s;
            }
            .no-results i {
                font-size: 3em;
                color: #888;
                animation: bounce 1.5s infinite;
            }
            @keyframes bounce {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(-10px);
                }
            }
            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }
        </style>
    </head>
    <body>
        <div class="row mt-3">
            <c:forEach var="item" items="${listaProd}">
                <div class="col-sm-12 col-md-6 col-lg-4 mt-1"> 
                    <div class="card">
                        <form method="post" action="CarritoControlador">
                            <img src="${item.imagen}" class="card-img-top" style="width: 100%; height: 280px;">
                            <div class="card-body">
                                <span class="badge text-bg-primary">${item.categoria.nombreCat}</span>
                                <h5 class="card-title" style="font-family: Verdana; font-family: 'PT Sans';">${item.nombre}</h5>
                                <p class="card-text">S/${item.precio}</p>
                                <input type="number" min="1" max="${item.stock}" name="cantidad" class="form-control" placeholder="Cantidad Adquirir." required="">

                                <div class="d-grid gap-2 mt-1">
                                    <button type="submit" class="btn btn-success">
                                        <i class="fa fa-cart-plus"></i> Agregar 
                                    </button>
                                </div>
                                <input type="hidden" name="id" value="${item.idProducto}">
                                <input type="hidden" name="accion" value="agregar">
                            </div>
                        </form>
                    </div>
                </div>
            </c:forEach>

            <!-- Mensaje de "sin resultados" -->
            <c:if test="${listaProd.size() == 0}">
                <div id="noResults" class="no-results col-12">
                    <i class="fa fa-search mb-3"></i><br>
                    <span>Sin resultados</span>
                </div>
            </c:if>
        </div>
    </body>
</html>


