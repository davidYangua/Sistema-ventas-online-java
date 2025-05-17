<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
    <title>Registrate</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300;500&display=swap" rel="stylesheet">
</head>

<body onload="(() => { document.querySelector('#nombre').focus() })()">
    
    <jsp:include page="template/Navbar.jsp" />
    <div class="container d-flex flex-column mt-3">
        <div class="card">
            <div class="card-header">
                <h5>Registro Cliente</h5>
            </div>
            <div class="card-body">
                <jsp:include page="./template/Mensaje.jsp" />
                <form action="ClienteControlador" method="post" onsubmit="return validatePasswords()">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Nombres:</label>
                            <input type="text" class="form-control" maxlength="100" required name="nombre" id="nombre" autocomplete="off" value="${cliente.nombres}">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Apellidos:</label>
                            <input type="text" class="form-control" maxlength="120" required name="apellido" id="apellido" autocomplete="off" value="${cliente.apellidos}">
                        </div>
                    </div>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">DNI: </label>
                            <input type="text" class="form-control" maxlength="8" name="dni" id="dni" autocomplete="off" value="${cliente.dni}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Telefono:</label>
                            <input type="text" class="form-control" maxlength="9" name="telefono" id="telefono" autocomplete="off" value="${cliente.telefono}">
                        </div>
                    </div>

                    <div class="mt-2">
                        <span class="badge text-bg-info">Datos Autentificación</span>
                        <hr />
                    </div>

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Correo electrónico: </label>
                            <input type="email" class="form-control" maxlength="80" name="correo" id="correo" autocomplete="off" value="${cliente.usuario.correo}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Contraseña:</label>
                            <input type="password" class="form-control" maxlength="80" name="password" id="password" required>
                            <div id="password-feedback" class="form-text mt-0"></div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Direccion:</label>
                            <input type="text" class="form-control" maxlength="300" name="direccion" id="direccion" value="${cliente.direccion}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Vuelva a escribir su Contraseña:</label>
                            <input type="password" class="form-control" maxlength="80" name="confirm_password" id="confirm_password" required>
                            <small id="password-match-text" class="form-text"></small>
                        </div>
                    </div>
                    <div class="mt-2 d-flex">
                        <input type="hidden" name="accion" value="guardar">
                        <button class="btn btn-primary" type="submit">Registrarse</button>
                        <a class="btn btn-danger ms-2" href="${pageContext.request.contextPath}/PagLogin.jsp">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="template/Footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    <script>
        const passwordInput = document.querySelector('#password');
        const feedback = document.querySelector('#password-feedback');

        passwordInput.addEventListener('input', function () {
            const password = passwordInput.value;

            if (password.length === 0) {
                // Mensaje si el campo está vacío
                feedback.innerHTML = '<i class="bi bi-x-circle-fill text-danger"></i> El campo contraseña es requerido';
                feedback.classList.add('text-danger');
            } else if (password.length < 8) {
                // Mensaje si la contraseña es débil
                feedback.innerHTML = '<i class="bi bi-x-circle-fill text-danger"></i> La contraseña es débil';
                feedback.classList.remove('text-success');
                feedback.classList.add('text-danger');
            } else {
                // Mensaje si la contraseña cumple con los requisitos
                feedback.innerHTML = '<i class="bi bi-check-circle-fill text-success"></i> La contraseña se ajusta a los requisitos';
                feedback.classList.remove('text-danger');
                feedback.classList.add('text-success');
            }
        });

        // Validación de coincidencia de contraseñas
        document.querySelector('#confirm_password').addEventListener('input', function () {
            const matchText = document.getElementById('password-match-text');
            const password = passwordInput.value;
            const confirmPassword = this.value;

            if (password === confirmPassword) {
                matchText.innerHTML = '<i class="bi bi-check-circle-fill text-success"></i> Las contraseñas coinciden';
                matchText.classList.remove('text-danger');
                matchText.classList.add('text-success');
            } else {
                matchText.innerHTML = '<i class="bi bi-x-circle-fill text-danger"></i> Las contraseñas no coinciden';
                matchText.classList.remove('text-success');
                matchText.classList.add('text-danger');
            }
        });

        // Validación final en el envío del formulario
        function validatePasswords() {
            const password = passwordInput.value;
            const confirmPassword = document.querySelector('#confirm_password').value;

            if (password !== confirmPassword) {
                alert('Las contraseñas no coinciden');
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
