<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Util.AuthFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Productos</title>
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    </head>
    <body>
        <!-- Validación de administrador -->
        <c:if test='${!AuthFilter.esAdmin(sessionScope.usuario)}'>
            <c:redirect url="LoginControlador?accion=redirect" />
        </c:if>

        <jsp:include page="../../template/NavbarPro.jsp" />

        <!-- Sección de Productos -->
        <div class="container-fluid mt-4">
            <div class="card p-4 shadow">
                <div class="card-header bg-dark text-white d-flex align-items-center justify-content-between">
                    <h4 class="mb-0"><i class="bi bi-list-check"></i> Gestión de Productos</h4>
                </div>
                <hr>
                <div class="mb-3">
                    <a href="ProductoControlador?accion=nuevo_producto&id=${usuario.id}" class="btn btn-success" title="Nuevo Producto">
                        <i class="fa fa-plus-circle"></i> Nuevo Producto
                    </a>
                </div>
                <div class="table-responsive">
                    <jsp:include page="../../template/Mensaje.jsp" />
                    <table class="table table-hover table-striped table-bordered text-center" id="tabla">
                        <thead class="table-primary">
                            <tr>
                                <th class="text-center">Imagen</th>  
                                <th class="text-center">Nombre</th>
                                <th class="text-center">Precio (S/)</th>
                                <th class="text-center">Stock</th>
                                <th class="text-center">Categoría</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${lista}">
                                <tr>
                                    <td><img src="${item.imagen}" style="width: 100px; height: 90px;" alt="Imagen Producto"></td>
                                    <td>${item.nombre}</td>
                                    <td>${item.precio}</td>
                                    <td>${item.stock}</td>
                                    <td>${item.categoria.nombreCat}</td>
                                    <td>
                                        <a href="ProductoControlador?accion=editar&idProd=${item.idProducto}" class="btn btn-info" title="Editar">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                        <a href="ProductoControlador?accion=eliminar&idProd=${item.idProducto}"
                                           onclick="return confirm('¿Está seguro que desea eliminar el producto ${item.nombre}?')"  
                                           class="btn btn-danger" title="Eliminar">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test='${lista.size() == 0}'>
                                <tr>
                                    <td colspan="6">No hay datos</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Sección de Categorías -->
        <div class="container-fluid mt-4">
            <div class="card p-4 shadow">
                <div class="card-header bg-dark text-white d-flex align-items-center justify-content-between">
                    <h4 class="mb-0"><i class="bi bi-tags-fill"></i> Gestión de Categorías</h4>
                </div>
                <hr>
                <div class="mb-3">
                    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#agregarModal" title="Nueva Categoría">
                        <i class="fa fa-plus-circle"></i> Nueva Categoría
                    </button>
                </div>
                <div class="table-responsive">
                    <jsp:include page="../../template/Mensaje.jsp" />
                    <table class="table table-hover table-striped table-bordered text-center" id="tablaCat">
                        <thead class="table-primary">
                            <tr>
                                <th class="text-center">Nro</th>
                                <th class="text-center">Nombre Categoría</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${categorias}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${item.nombreCat}</td>
                                    <td>
                                        <button type="button" class="btn btn-info" 
                                                data-bs-toggle="modal" 
                                                data-bs-target="#EditModal" 
                                                data-id="${item.idCategoria}" 
                                                data-nombre="${item.nombreCat}" 
                                                title="Editar">
                                            <i class="fa fa-edit"></i>
                                        </button>
                                        <a href="AdminControlador?accion=eliminar_categoria&id=${item.idCategoria}"
                                           onclick="return confirm('¿Está seguro que desea eliminar la categoría ${item.nombreCat}?')"  
                                           class="btn btn-danger" title="Eliminar">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Modal para Añadir Categoría -->
        <div class="modal fade" id="agregarModal" tabindex="-1" aria-labelledby="modalAgregarLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title" id="modalAgregarLabel">Añadir Nueva Categoría</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="AdminControlador" method="post">
                        <div class="modal-body">
                            <label class="form-label">Nombre</label>
                            <input type="text" class="form-control" name="nuevaCat" required autocomplete="off">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                            <input type="hidden" name="accion" value="agregar_categoria">
                            <button type="submit" class="btn btn-success">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal para Editar Categoría -->
        <div class="modal fade" id="EditModal" tabindex="-1" aria-labelledby="modalEditLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-warning">
                        <h5 class="modal-title" id="modalEditLabel">Editar Categoría</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="AdminControlador" method="post">
                        <div class="modal-body">
                            <label class="form-label">Nombre</label>
                            <input id="edit-cat" type="text" class="form-control" name="editCat" required autocomplete="off">
                            <input type="hidden" id="edit-cat-id" name="id">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                            <input type="hidden" name="accion" value="editar_categoria">
                            <button type="submit" class="btn btn-success">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="../../template/Footer.jsp" />

        <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/dataTables.bootstrap5.min.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {
                $('#tabla, #tablaCat').DataTable({
                    language: {
                        decimal: "",
                        emptyTable: "No hay información",
                        info: "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
                        infoEmpty: "Mostrando 0 to 0 of 0 Entradas",
                        infoFiltered: "(Filtrado de _MAX_ total entradas)",
                        lengthMenu: "Mostrar _MENU_ Entradas",
                        loadingRecords: "Cargando...",
                        processing: "Procesando...",
                        search: "Buscar:",
                        zeroRecords: "Sin resultados encontrados",
                        paginate: {
                            first: "Primero",
                            last: "Ultimo",
                            next: "Siguiente",
                            previous: "Anterior"
                        }
                    }
                });
            });

            // Llenar modal de edición con datos
            $('#EditModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var id = button.data('id');
                var nombre = button.data('nombre');
                var modal = $(this);
                modal.find('#edit-cat-id').val(id);
                modal.find('#edit-cat').val(nombre);
            });
        </script>
    </body>
</html>
