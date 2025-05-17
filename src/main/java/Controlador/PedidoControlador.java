package Controlador;

import Modelo.Cliente;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Modelo.Usuario;
import ModeloDAO.PedidoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PedidoControlador extends HttpServlet {
    
    private PedidoDAO ped = new PedidoDAO();
    private String PagMisPedidos = "PagMisPedidos.jsp";
    private String pagDetInfo = "vista/includes/ModalInfoDetallePedido.jsp";
    private Usuario usuario;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        usuario = request.getSession().getAttribute("usuario") != null ? (Usuario)request.getSession().getAttribute("usuario") : new Usuario();
        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");
        
        switch (accion.toLowerCase()) {
            case "procesar":
                Procesar(request, response);
                break;
            case "mis_pedidos":
                MisPedidos(request, response);
                break;
            case "detalle":
                VerDetalle(request, response);
                break;
        }
    }
    
    
    protected void Procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String direccion = request.getParameter("direccion");
        int metodoPag = Integer.parseInt(request.getParameter("metodo"));
        String comentario = request.getParameter("comentario") != null ? request.getParameter("comentario") : "";
        
        Pedido p = new Pedido();
        Cliente c = new Cliente();
        c.setIdCliente(usuario.getId());
        p.setCliente(c);
        p.setDetalles((ArrayList<DetallePedido>)request.getSession().getAttribute("carrito"));
        p.setMontoTotal(Double.parseDouble(request.getSession().getAttribute("total").toString()));
        
        String msg = ped.GuardarPedido(p, metodoPag, direccion, comentario);
        
        if(msg.equals("OK")) {
            request.getSession().setAttribute("carrito", null);
            request.getSession().setAttribute("total", null);
            request.getSession().setAttribute("success", "Felicitaciones!! Tu pedido fue procesado de forma correcta.");
            response.sendRedirect("PedidoControlador?accion=mis_pedidos");
        } else {
            request.getSession().setAttribute("error", msg);
            response.sendRedirect("CarritoControlador?accion=listar");
        }
    }
    
    protected void MisPedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int idCliente = usuario.getId();
        request.setAttribute("lista", ped.ListarPorCliente(idCliente));
        request.getRequestDispatcher(PagMisPedidos).forward(request, response);
    }
    
    protected void VerDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String nroPed = request.getParameter("nro");
        
        PedidoDAO p = new PedidoDAO();
        
        request.setAttribute("pedido", p.BuscarPorId(nroPed));
        request.setAttribute("ver", 1);
        request.setAttribute("detalles", p.ListarDetallePorPedido(nroPed));
        request.getRequestDispatcher(pagDetInfo).forward(request, response);
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
