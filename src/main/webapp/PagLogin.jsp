<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Iniciar Sesión</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300;500&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/bootstrapValidator.css">
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery-1.10.2.min.js"></script>
        <script src="js/bootstrapValidator.js"></script>
    </head>
    <style>
        .password-container{
            position: relative;
        }
        .password-container i{
            position: absolute;
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
            cursor: pointer;
        }
    </style>
    <body onload="(() => {
                document.querySelector('#us').focus()
            })()">

        <jsp:include page="template/Navbar.jsp" />

        <div class="container mt-4 col-lg-4">
            <div class="card col-sm-9">
                <div class="card-body">
                    <form id="id_form" action="LoginControlador" method="post">
                        <div class="container text-center">
                            <h3 for="l">RedPoint</h3>
                            <img src="Images/login.jpg" width="200px" alt="registrar"><br>
                            <label>Iniciar Sesión</label>
                        </div>
                        <jsp:include page="template/Mensaje.jsp" />
                        <div class="form-group">
                            <label for="us" class="form-label">Correo Electrónico</label>
                            <input id="us" class="form-control" type="email" name="correo" autocomplete="off" required placeholder="ejemplo@gmail.com">
                        </div>
                        <div class="form-group">
                            <label for="c" class="form-label">Contraseña</label>
                            <div class="password-container">
                                <input id="c" class="form-control" type="password" name="password" required><i id="showPassword"x class="bi bi-eye-slash-fill"></i>
                            </div>
                            <p class="mt-2 float-end" style="font-size: 13px;"><a href="#">¿Olvidaste tu contraseña?</a></p>
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="accion" value="login">
                            <button type="submit" class="btn btn-dark form-control mt-0">Ingresar</button>
                            <div class="d-flex justify-content-center mt-2" style="font-size: 14px;">
                                <span>¿No tienes cuenta?</span>
                                <a href="PagRegCliente.jsp" style="margin-left: 5px;">Registrate</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="template/Footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js" integrity="sha384-fbbOQedDUMZZ5KreZpsbe1LCZPVmfTnH7ois6mU1QK+m14rQ1l2bGBq41eYeM/fS" crossorigin="anonymous"></script>
    </body>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#id_form').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    correo: {
                        validators: {
                            notEmpty: {
                                message: 'El correo es requerido'
                            },
                            emailAddress: {
                                message: 'Formato no válido'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: 'La contraseña es requerido'
                            }
                        }
                    },
                }
            });

            // Validate the form manually
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
        });
    </script>
</html>