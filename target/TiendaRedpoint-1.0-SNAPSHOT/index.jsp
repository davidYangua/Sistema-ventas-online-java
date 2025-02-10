<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Util.AuthFilter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="Images/logotipo.jpg">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300;500&display=swap" rel="stylesheet">
        <title>Red Point</title>
        <style>
            #btnSubir {
                display: none;
                z-index: 1000;
            }
        </style>
    </head>

    <body>
        
        <c:if test="${not empty sessionScope.error}">
            <c:redirect url="LoginControlador?accion=redirect" />
        </c:if>
        
        <jsp:include page="template/Navbar.jsp" />
        <section id="hero">
            <div class="container">
                <h1>Menús Divetidos y Super Económicos</h1>
                <form action="PagDelivery.jsp">
                    <button type="submit" class="button-platos">DELIVERY</button>
                </form>

            </div>
        </section>
        <section id="about">
            <div class="container">
                <h2>¿Quiénes Somos?</h2>
                <p>
                    Somos Red Point, aquí nos enorgullece ofrecer una experiencia gastronómica única y diversa que se adapta a
                    todos los gustos y
                    presupuestos. Nuestra propuesta gastronómica ofrece dos alternativas, siempre de la misma calidad. Con el
                    menú diario hemos querido reinventar una apuesta tradicional sin perder la esencia. En la otra hemos dado
                    rienda
                    suelta a nuestra imaginación y a nuestro deseo de innovar un menú super económico. Valoramos la importancia
                    de una
                    alimentación equilibrada y saludable, por lo que utilizamos ingredientes frescos y de calidad en todas
                    nuestras preparaciones. Nuestro equipo de chefs talentosos se esfuerza por ofrecer platos sabrosos y
                    nutritivos que satisfagan tus necesidades nutricionales mientras disfrutas de una experiencia culinaria
                    inolvidable. Ya sea que estés buscando un lugar para disfrutar de una comida deliciosa después de las
                    clases, celebrar un logro académico o simplemente relajarte con amigos, Red Point es el lugar
                    perfecto para ti. Ven y descubre la combinación perfecta entre calidad, sabor y precios asequibles.
                </p>
            </div>
        </section>
        <section id="menus">
            <div class="containers">
                <h2>Nuestros Menús</h2>
                <div class="platos">
                    <div class="carta">
                        <h3>Menús Saludables</h3>
                        <p class="mt-5">
                            Una opción deliciosa y equilibrada para aquellos que buscan mantener un estilo de vida saludable.
                            Diseñados cuidadosamente por nuestros expertos en nutrición y chefs talentosos,
                            garantizando que cada plato sea una combinación perfecta de sabor y nutrientes.
                        </p>

                    </div>
                    <div class="carta">
                        <h3>Menús Económicos</h3>
                        <p class="mt-5">
                            Una opcion deliciosa para que puedas disfrutar de una excelente comida sin comprometer
                            tu presupuesto. Nuestros menús económicos están diseñados pensando en brindarte una experiencia
                            culinaria satisfactoria sin dejar un agujero en tu bolsillo.
                        </p>
                    </div>
                    <div class="carta">
                        <h3>Menús más pedidos</h3>
                        <p class="mt-5">
                            Nuestros platos más populares que han conquistado el paladar de nuestros clientes. Estos platos han
                            sido cuidadosamente seleccionados y han ganado reconocimiento por su sabor excepcional y su
                            capacidad de deleitar a todos los comensales.
                        </p>
                    </div>
                    <div class="carta">
                        <h3>Platos a la Carta</h3>
                        <p class="mt-5">
                            Te invitamos a explorar una experiencia culinaria personalizada y única. Nuestros platos a la carta
                            te permiten seleccionar cuidadosamente cada componente de tu comida, brindándote la libertad de
                            crear el plato perfecto según tus gustos y preferencias.
                        </p>
                    </div>
                </div>
                <form action="PagDelivery.jsp">
                    <button type="submit" class="button-platos mt-4">Ver Platos</button>
                </form>
            </div>
        </section>

        <section id="lugar">
            <div class="container">
                <center>
                    <iframe
                        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3476.5993329429!2d-77.00964701871555!3d-12.019382173416767!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x9105c5f4dca199ff%3A0xdd830711f8606448!2sBarrio%20Marino!5e0!3m2!1ses-419!2spe!4v1683590263452!5m2!1ses-419!2spe"
                        width="90%" height="120" style="border:1px;" allowfullscreen="" loading="lazy"
                        referrerpolicy="no-referrer-when-downgrade"></iframe>
                </center>
            </div>
        </section>

        <button id="btnSubir" class="btn btn-primary position-fixed bottom-0 end-0 m-4" onclick="subirPagina()">
            <i class="bi bi-arrow-up"></i>
        </button>

        <jsp:include page="template/Footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </body>

    <script>
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
</body>

</html>
