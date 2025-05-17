<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Util.AuthFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Productos</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
    </head>
    <body>
        
        <c:if test='${!AuthFilter.esAdmin(sessionScope.usuario)}'>
            <c:redirect url="LoginControlador?accion=redirect" />
        </c:if>
        
        <jsp:include page="../../template/NavbarPro.jsp" />

        <div class="container d-flex flex-column mt-5">
            <div class="card">
                <div class="card-header">
                    <h5>${(producto.idProducto == 0 || producto == null) ? 'Nuevo': 'Editar'} Producto</h5>
                </div>
                <div class="card-body">
                    <jsp:include page="../../template/Mensaje.jsp" />
                    <form action="ProductoControlador" method="post">
                        <div class="mb-3 row">
                            <label  class="col-sm-2 col-form-label">Nombre:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="nombre" id="nombre" value="${producto.nombre}" maxlength="50" required="">
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label class="col-sm-2 col-form-label">Categoría:</label>
                            <div class="col-sm-10">
                                <select class="form-select" name="categoria" id="cat" aria-label="Default select example" required="">
                                    <option selected>Seleccionar...</option>
                                    <c:forEach var="item" items="${lista}" varStatus="loop">
                                        <option value="${item.idCategoria}">${item.nombreCat}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>


                        <div class="mb-3 row">
                            <label  class="col-sm-2 col-form-label">Precio:</label>
                            <div class="col-sm-10">
                                <input type="number" step="0.1"  class="form-control" name="precio" id="precio" value="${producto.precio}" required="">
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label  class="col-sm-2 col-form-label">Stock:</label>
                            <div class="col-sm-10">
                                <input type="number"  class="form-control" name="stock" id="stock" value="${producto.stock}" min='0' required="">
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label  class="col-sm-2 col-form-label">Url Imagen</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="imagen" id="imagen" value="${producto.imagen}" required="">

                                <c:if test='${producto.imagen != null}'>
                                    <img src="${producto.imagen}" style="width: 100px;height: 90px;">
                                </c:if>
                            </div>
                        </div>
                        <div >
                            <input type="hidden" name="accion" value="guardar">
                            <c:if test="${producto.idProducto != null}">
                                <input type="hidden" name="id" value="${producto.idProducto}">
                            </c:if> 
                            <input type="hidden" name="idAdmin" value="${id}">
                            <button class="btn btn-success" type="submit">Guardar</button>
                            <a class="btn btn-danger" type="buton" href="${pageContext.request.contextPath}/AdminControlador?accion=productos" >Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </body>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    <script>
        if(${producto.categoria.idCategoria}!== null) {
            document.querySelector('#cat').selectedIndex = ${producto.categoria.idCategoria};
        }
    </script>
</html>