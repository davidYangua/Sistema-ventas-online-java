<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Contáctenos</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
        <style>
            body {
                font-family: Arial, sans-serif;
            }

            /* Botón de WhatsApp */
            .whatsapp-button {
                position: fixed;
                bottom: 90px; /* Ajuste de posición para evitar superposición con el botón "Subir al inicio" */
                right: 20px;
                background-color: #25d366;
                color: white;
                width: 60px;
                height: 60px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 28px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
                cursor: pointer;
                transition: transform 0.3s ease;
            }

            .whatsapp-button:hover {
                transform: scale(1.1);
            }

            /* Modal de WhatsApp expandido */
            .whatsapp-modal {
                display: none;
                position: fixed;
                bottom: 160px; /* Ubicación más arriba para evitar solapamiento */
                right: 20px;
                background-color: #fff;
                border: 1px solid #ddd;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                border-radius: 8px;
                padding: 15px;
                width: 220px;
                text-align: center;
            }

            .whatsapp-modal p {
                margin: 0;
                font-weight: bold;
                color: #25d366;
            }

            .whatsapp-modal a {
                margin-top: 10px;
                text-decoration: none;
                color: #fff;
                font-weight: bold;
            }

            /* Mostrar el modal al hacer clic en el botón */
            .whatsapp-button.active + .whatsapp-modal {
                display: block;
            }

        </style>
    </head>
    <body>
        <div class="whatsapp-button" id="whatsappButton" data-bs-toggle="tooltip" data-bs-placement="left" title="¡Bienvenido! Escriba aquí su consulta">
            <i class="bi bi-whatsapp" id="whatsappIcon"></i>
        </div>

        <div class="whatsapp-modal" id="whatsappModal">
            <p>Atención al cliente</p>
            <a href="https://api.whatsapp.com/send?phone=51923887662" target="_blank" class="btn btn-success">Contactar vía WhatsApp</a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const whatsappButton = document.getElementById("whatsappButton");
                const whatsappModal = document.getElementById("whatsappModal");
                const whatsappIcon = document.getElementById("whatsappIcon");

                // Tooltip de Bootstrap
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                    return new bootstrap.Tooltip(tooltipTriggerEl);
                });

                // Mostrar el modal al hacer clic en el botón de WhatsApp
                whatsappButton.addEventListener("click", () => {
                    whatsappButton.classList.toggle("active");
                    whatsappModal.classList.toggle("show");

                    // Cambiar el ícono a 'X' cuando el modal esté abierto y volver al ícono de WhatsApp cuando esté cerrado
                    if (whatsappButton.classList.contains("active")) {
                        whatsappIcon.classList.remove("bi-whatsapp");
                        whatsappIcon.classList.add("bi-x");
                    } else {
                        whatsappIcon.classList.remove("bi-x");
                        whatsappIcon.classList.add("bi-whatsapp");
                    }
                });
            });

        </script>
    </body>
</html>
