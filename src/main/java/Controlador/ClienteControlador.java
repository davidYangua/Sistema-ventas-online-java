package Controlador;

import Modelo.Cliente;
import Modelo.Usuario;
import ModeloDAO.ClienteDAO;
import Util.Hash;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClienteControlador extends HttpServlet {
    
    private ClienteDAO cliDAO = new ClienteDAO();
    private String pagNuevo = "PagRegCliente.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");
        switch (accion.toLowerCase()) {
            case "nuevo":
                nuevo(request, response);
                break;
            case "guardar":
                guardar(request, response);
                break;
        }
        
    }
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        request.setAttribute("cliente", new Cliente());
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }
    
    protected void guardar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String salt = "";
        
        Cliente cli = new Cliente();
        Usuario usuario = new Usuario();
        cli.setNombres(request.getParameter("nombre"));
        cli.setApellidos(request.getParameter("apellido"));
        cli.setDni(request.getParameter("dni"));
        cli.setTelefono(request.getParameter("telefono"));
        cli.setDireccion(request.getParameter("direccion"));
        usuario.setCorreo(request.getParameter("correo"));
        salt = Hash.generarSalt();
        usuario.setPassword(Hash.sha256(request.getParameter("password"), salt));
        cli.setUsuario(usuario);
        
        String msg = cliDAO.guardar(cli,salt);
        
        if (msg.equals("OK")) {
            request.getSession().setAttribute("success", "Los datos del cliente se registraron de forma correcta");
            response.sendRedirect("ClienteControlador?accion=nuevo");
        } else {
           cli.getUsuario().setPassword(request.getParameter("password"));
           request.setAttribute("cliente", cli);
           request.getSession().setAttribute("error", msg);
           request.getRequestDispatcher(pagNuevo).forward(request, response);
        }
    
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
