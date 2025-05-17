<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Carrito</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <title>Red Point</title>
        <style>
            .btn-circle {
                width: 30px;
                height: 30px;
                text-align: center;
                padding: 6px 0;
                font-size: 12px;
                line-height: 1.428571429;
                border-radius: 15px;
            }
            .btn-whatsapp {
                background-color: #25D366;
                color: white;
                border: none;
            }

            .btn-whatsapp:hover {
                background-color: #1ebc57;
                color: white;
            }

            .form-check-label {
                font-size: 1.2rem; /* Tamaño de letra mayor */
                margin-left: 10px; /* Espaciado entre radio y texto */
            }

            .form-check-input {
                transform: scale(1.2); /* Agrandar los botones de selección */
            }

        </style>
    </head>
    <body>
        
        <jsp:include page="template/Navbar.jsp" />

        <div class="container-fluid mt-3">
            <div class="card">
                <div class="card-body">
                    <div class="text-center">
                        <span class="badge text-bg-success">Mi Carrito</span>
                        <hr />
                        <jsp:include page="template/Mensaje.jsp" />
                        <div class="row">
                            <div class="col-lg-9">                 
                                <table class="table table-bordered">
                                    <thead class="thead-light">
                                        <tr class="text-center">
                                            <th colspan="2"></th>                               
                                            <th>Producto</th>
                                            <th>Precio</th>
                                            <th>Cantidad</th>                       
                                            <th>Total</th>                       
                                            <th>Acciones</th>                       
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="item" items="${sessionScope.carrito}" varStatus="loop"> 
                                            <tr class="text-center">      
                                                <td>
                                                    <a href="javascript:fnInfoEmp(${item.producto.nombre})"  class="btn btn-info btn-sm btn-circle" title="">
                                                        <b>${(loop.index+1)}</b> <!-- comment  <i class="fa fa-info"></i> -->
                                                    </a>
                                                </td>
                                                <td>
                                                    <img  src="${item.producto.imagen}" class="card-img-top" style="width: 80px;height: 60px;">
                                                </td>
                                                <td>${item.producto.nombre}</td>             
                                                <td>${item.producto.precio}</td>
                                                <td>${item.cantidad}</td>   
                                                <td> <fmt:formatNumber type = "number" 
                                                                  maxFractionDigits = "2" value = "${item.Total()}" /></td>                           
                                                <td class="text-center">                                         
                                                    <a  href="CarritoControlador?accion=eliminar&indice=${loop.index}"
                                                        class="btn btn-danger btn-sm" title="Quitar del carrito"><i class="fas fa-trash-alt"></i></a>                                            

                                                </td>                        
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${sessionScope.carrito==null or  sessionScope.carrito.size() == 0}">
                                            <tr class="text-center">
                                                <td colspan="7">No se encontraron registros.</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>                    
                            </div>

                            <div class="col-lg-3">                  
                                <div class="card">
                                    <form>
                                        <div class="card-header">
                                            Cuenta
                                        </div>
                                        <div class="card-body">
                                            <label>Total a Pagar:</label>
                                            <span class="form-control text-center" style="font-weight: bold;font-size: 22px;">
                                                S/ <fmt:formatNumber type = "number" maxFractionDigits = "2" value = "${sessionScope.total}" /></span>
                                        </div>
                                        <div class="card-footer">
                                            <c:if test="${sessionScope.carrito==null or  sessionScope.carrito.size() == 0}">
                                                <a href="ProductoControlador?accion=categorias" class="btn btn-danger btn-block disabled">Seguir Comprando</i></a>
                                                <a href="#" class="btn btn-primary btn-block disabled">Procesar Compra</i></a>
                                            </c:if>
                                            <c:if test="${sessionScope.carrito!=null and  sessionScope.carrito.size() >0 and sessionScope.usuario != null}">
                                                <a href="ProductoControlador?accion=categorias" class="btn btn-danger btn-block">Seguir Comprando</i></a>
                                                <button type="button"
                                                        data-bs-toggle="modal" data-bs-target="#modalPago"
                                                        class="btn btn-primary btn-block">Procesar Compra</button>
                                            </c:if>
                                            <c:if test="${sessionScope.carrito!=null and  sessionScope.carrito.size() >0 and sessionScope.usuario == null}">
                                                <a href="ProductoControlador?accion=categorias" class="btn btn-danger btn-block">Seguir Comprando</i></a>
                                                <button type="button"
                                                        data-bs-toggle="modal" data-bs-target="#modalLogin"
                                                        class="btn btn-primary btn-block">Procesar Compra</button>
                                            </c:if>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalPago" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fs-5">::: Procesar Compra :::</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body text-justify">
                        <p>Estimado (a) <span>${sessionScope.usuario.username}</span></p>
                        <p>Para poder procesar la compra es requerido realizar el cobro correspondiente através de uno de nuestros métodos de pago.
                            Escanee el código QR o al siguiente reqaliza el pago al siguiente número: <span style="font-weight: bold;">923887662.</span></p>
                        <p>Solo se le cobrará el importe correspondiente.</p>
                        <label style="font-weight: bold;">Total a Pagar: <span >S/ <fmt:formatNumber type = "number" 
                                          maxFractionDigits = "2" value = "${sessionScope.total}" /></span></label>
                        <hr />

                        <div class="row g-3 mt-2 d-flex justify-content-center align-items-center">
                            <div class="container">
                                <img height="210px" src="Images/QR-Yape-Plin (1).jpg">
                            </div>

                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button"
                                data-bs-toggle="modal" data-bs-target="#modalConfirmacion"
                                class="btn btn-success btn-block" data-dismiss="modal" >Confirmar Pago</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- modal -->
        <div class="modal fade" id="modalConfirmacion" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title fs-5">::: Procesar Compra :::</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="PedidoControlador" method="post">
                        <div class="modal-body text-justify">
                            <label style="font-weight: bold;">Confirmanos tu dirección: </label><br>
                            <input type="text" class="form-control" name="direccion" required="">
                            <hr>
                            <div class="container my-3">
                                <span style="font-weight: bold;">Confirme el método de pago que realizó: </span><br>
                                <div class="form-check">
                                    <input class="form-check-input" name="metodo" type="radio" value="1" id="yape" required>
                                    <label class="form-check-label" for="yape" style="color: #7C4DFF; font-weight: bold;">
                                        Yape
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" name="metodo" type="radio" value="2" id="plin" required>
                                    <label class="form-check-label" for="plin" style="color: #00A1E0; font-weight: bold;">
                                        Plin
                                    </label>
                                </div>
                            </div>
                            <hr>
                            <label style="font-weight: bold;">Déjanos un comentario para seguir mejorando: </label><br>
                            <textarea name="comentario" class="form-control w-100 mb-3" rows="3" placeholder="Escribe aquí..."></textarea>

                            <hr>
                            <label style="font-weight: bold;">Adjunta tu captura del pago realizado vía WhatsApp (opcional):</label><br>
                            <div class="text-center">
                                <a href="https://api.whatsapp.com/send?phone=51923887662" target="_blank" class="btn btn-success btn-lg btn-whatsapp">
                                    <i class="bi bi-whatsapp me-2"></i> Enviar a WhatsApp
                                </a>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="hidden" name="accion" value="procesar">
                            <button type="submit" class="btn btn-success">Finalizar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal de Login -->
        <div class="modal fade" id="modalLogin" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" style="max-width: 400px;">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="text-center;" class="modal-title fs-5">::: Debes iniciar sesión :::</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="text-center mb-4">
                            <h3>RedPoint</h3>
                            <!-- Imagen centrada y ajustada -->
                            <img src="Images/login.jpg" class="img-fluid mb-2" alt="registrar" style="max-width: 150px;">
                            <label class="d-block mt-2">Iniciar Sesión</label>
                        </div>
                        <form id="id_form" action="LoginControlador" method="post">
                            <jsp:include page="template/Mensaje.jsp" />
                            <div class="form-group mb-3">
                                <label for="us" class="form-label">Correo Electrónico</label>
                                <input id="us" class="form-control" type="email" name="correo" autocomplete="off" required placeholder="ejemplo@gmail.com">
                            </div>
                            <div class="form-group mb-3 position-relative">
                                <label for="c" class="form-label">Contraseña</label>
                                <!-- Campo de contraseña con ícono del ojo correctamente posicionado -->
                                <div class="input-group">
                                    <input id="c" class="form-control" type="password" name="password" required>
                                    <span class="input-group-text" style="cursor: pointer;">
                                        <i id="showPassword" class="bi bi-eye-slash-fill"></i>
                                    </span>
                                </div>
                                <p class="mt-2 text-end" style="font-size: 13px;"><a href="#">¿Olvidaste tu contraseña?</a></p>
                            </div>
                            <input type="hidden" name="accion" value="login">
                            <input type="hidden" name="procesar" value="procesar">
                            <button type="submit" class="btn btn-dark w-100">Ingresar</button>
                            <div class="d-flex justify-content-center mt-3" style="font-size: 14px;">
                                <span>¿No tienes cuenta?</span>
                                <a href="PagRegCliente.jsp" class="ms-1">Regístrate</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <br />
        <div id="modalEmpResult"></div>
        <jsp:include page="template/Footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        <script>
            $('#validateBtn').click(function () {
                $('#id_form').bootstrapValidator('validate');
            });

            $("#showPassword").click(function () {
                var passwordField = $("#c");
                var passwordIcon = $("#showPassword");

                if (passwordField.attr("type") === "password") {
                    passwordField.attr("type", "text");
                    passwordIcon.removeClass("bi bi-eye-slash-fill").addClass("bi bi-eye-fill"); // Cambia el icono a un ojo abierto
                } else {
                    passwordField.attr("type", "password");
                    passwordIcon.removeClass("bi bi-eye-fill").addClass("bi bi-eye-slash-fill"); // Cambia el icono a un ojo cerrado
                }
            });
        </script>
    </body>
</html>
