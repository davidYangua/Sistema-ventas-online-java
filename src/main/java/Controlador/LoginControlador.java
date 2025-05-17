package Controlador;

import Modelo.Usuario;
import ModeloDAO.UsuarioDAO;
import Util.Constantes;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginControlador extends HttpServlet {

    private UsuarioDAO log = new UsuarioDAO();
    private String pagLogin = "PagLogin.jsp";
    private String pagAdmin = "AdminControlador?accion=inicio";
    private String pagCliente = "index.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

        switch (accion.toLowerCase()) {
            case "login":
                Login(request, response);
                break;
            case "logout":
                Logout(request, response);
                break;
            case "redirect":
                Redirect(request, response);
                break;
        }
    }

    protected void Login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String correo = request.getParameter("correo");
        String password = request.getParameter("password");
        String procesar = request.getParameter("procesar") == null ? "" : request.getParameter("procesar");

        Usuario obj = log.autentificar(correo, password);

        if (obj != null) {
            if (obj.getEstado() != 0) {
                request.getSession().setAttribute("usuario", obj);
                String redirect = "";

                switch (obj.getRol().getNombreRol()) {
                    case Constantes.ROL_ADMINISTRADOR:
                        redirect = pagAdmin;
                        break;
                    case Constantes.ROL_CLIENTE:
                        if (procesar.equals("procesar")) {
                            redirect = "PagCarrito.jsp";
                            break;
                        } else {
                            redirect = pagCliente;
                            break;
                        }

                }
                response.sendRedirect(redirect);
            } else {
                request.getSession().setAttribute("error", "Su usuario está restringido");
                response.sendRedirect(pagLogin);
            }

        } else {
            request.getSession().setAttribute("error", "Correo y/o contraseña incorrectos");
            response.sendRedirect(pagLogin);
        }
    }

    protected void Logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getSession().removeAttribute("usuario");
        response.sendRedirect(pagLogin);
    }

    protected void Redirect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        request.getSession().setAttribute("error", "Su sesión ha expirado, por favor ingrese nuevamente");
        response.sendRedirect(pagLogin);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
