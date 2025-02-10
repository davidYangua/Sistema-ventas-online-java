<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300;500&display=swap" rel="stylesheet">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: Arial, sans-serif;
            }

            .contact-section {
                background-color: #f8f9fa;
                padding: 50px 20px;
                text-align: center;
            }

            .container {
                max-width: 800px;
                margin: 0 auto;
            }

            .section-title {
                font-size: 24px;
                color: #800000;
                margin-bottom: 20px;
            }

            .contact-info {
                color: #333;
                font-size: 16px;
                line-height: 1.5;
            }

            .contact-info p {
                margin: 5px 0;
            }

            #map-section {
                width: 100%;
                height: 400px;
            }

            iframe {
                width: 100%;
                height: 100%;
                border: none;
            }

            .breadcrumb {
                background-color: transparent;
                font-size: 1rem;
            }
            .breadcrumb-item a {
                text-decoration: none;
                color: #6c757d;
            }
            .breadcrumb-item.active {
                color: #adb5bd;
            }
            .breadcrumb-item + .breadcrumb-item::before {
                content: "/";
                color: #adb5bd;
                padding: 0 5px;
            }

        </style>
        <title>Contáctenos</title>
    </head>
    <body>
        
        <jsp:include page="template/Navbar.jsp" />

        <section class="contact-section">
            <div class="container">
                <h2 class="section-title" style="font-size: 40px; font-weight: bold;">Contáctenos</h2>
                <div class="contact-info">
                    <nav aria-label="breadcrumb" class="d-flex justify-content-center">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index.jsp">Inicio</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Contáctenos</li>
                        </ol>
                    </nav>
                    <br><br>
                    <table class="table table-light">
                        <tbody>
                            <tr>
                                <td><p><strong>Horario de atención:</p></td>
                                <td></strong> De lunes a viernes de 09:00 a.m. a 06:00 p.m.</p></td>
                            </tr>
                            <tr>
                                <td><p><strong>Dirección:</strong><p></td>
                                <td><p>Av. Victor Raúl Halla de la Torre, Paita – Piura</p></td>
                            </tr>
                            <tr>
                                <td><p><strong>RUC:</p></td>
                                <td><p>207263459</p></td>
                            </tr>
                            <tr>
                                <td><p><strong>Razón Social:</strong></p></td>
                                <td><p>Redpoint</p></td>
                            </tr>
                            <tr>
                                <td><p><strong>Correo electrónico – Mesa de Partes:</p></td>
                                <td><p></strong> redpont@gmail.com</p></td>
                            </tr>
                            <tr>
                                <td><p><strong>Número de consultas:</strong></p></td>
                                <td><p> 923887662</p></td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            </div>
        </section>
        <section id="map-section">
            <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3476.5993329429!2d-77.00964701871555!3d-12.019382173416767!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x9105c5f4dca199ff%3A0xdd830711f8606448!2sBarrio%20Marino!5e0!3m2!1ses-419!2spe!4v1683590263452!5m2!1ses-419!2spe"
                width="100%" height="400" style="border:0;" allowfullscreen="" loading="lazy"
                referrerpolicy="no-referrer-when-downgrade"></iframe>
        </section>
        <jsp:include page="template/btnWpp.jsp" />
        <br><br><br>
        <jsp:include page="template/Footer.jsp" />
    </body>

</html>
