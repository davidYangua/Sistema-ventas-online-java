<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<style>
    .footer {
        background-color: #333; /* Fondo oscuro */
        color: white;
        padding: 2rem 0;
    }

    .footer-container {
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        max-width: 1200px;
        margin: auto;
    }

    .footer-section {
        flex: 1;
        min-width: 250px;
        margin: 1rem;
        text-align: center;
    }

    .footer-section h2 {
        color: #ffcc00; /* Color llamativo */
        margin-bottom: 1rem;
        font-size: 1.5rem;
    }

    .social-links a {
        margin: 0 0.5rem;
        color: white;
        font-size: 1.5rem;
        transition: color 0.3s ease;
    }

    .social-links a:hover {
        color: #ffcc00;
    }

    .links ul {
        list-style: none;
        padding: 0;
    }

    .links ul li {
        margin: 0.5rem 0;
    }

    .links ul li a {
        color: white;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    .links ul li a:hover {
        color: #ffcc00;
    }

    .footer-bottom {
        text-align: center;
        margin-top: 1rem;
        font-size: 0.9rem;
        color: #ccc;
    }

    .contact p {
        margin: 0.5rem 0;
        font-size: 0.9rem;
    }

</style>
<br />
<footer class="footer">
    <div class="footer-container">
        <!-- Redes Sociales -->
        <div class="footer-section social-media">
            <h2>Síguenos</h2>
            <div class="social-links">
                <a href="#"><i class="bi bi-facebook"></i></a>
                <a href="#"><i class="bi bi-instagram"></i></a>
                <a href="#"><i class="bi bi-twitter"></i></a>
                <a href="#"><i class="bi bi-youtube"></i></a>
            </div>
        </div>

        

        <!-- Contacto -->
        <div class="footer-section contact">
            <h2>Contacto</h2>
            <p><i class="bi bi-geo-alt-fill"></i>Av. Victor Raúl Halla de la Torre</p>
            <p><i class="bi bi-telephone-fill"></i> +51 783 456 789</p>
            <p><i class="bi bi-envelope-fill"></i> redpont@gmail.com</p>
        </div>
    </div>

    <!-- Final -->
    <div class="footer-bottom">
        <p>&copy; 2024 All rights reserved | REDPONT</p>
    </div>
</footer>
