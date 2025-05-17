package Controlador;

import Modelo.DetallePedido;
import Modelo.Producto;
import ModeloDAO.ProductoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarritoControlador extends HttpServlet {
    
    private ProductoDAO prod = new ProductoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");
        switch (accion.toLowerCase()) {
            case "agregar": 
                AgregarCarrito(request, response);
                break;
            case "eliminar":
                EliminarCarrito(request, response);
                break;
            case "listar":
                ListarCarrito(request, response);
                break;
        }
    }
    
    protected void AgregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        List<DetallePedido>lista = ObtenerSession(request);
        
        int idProd = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
        int cantidad = request.getParameter("cantidad") == null ? 0 : Integer.parseInt(request.getParameter("cantidad"));
        
        Producto p = prod.getProducto(idProd);
        int indice = BuscarPorId(lista, idProd);
        
        DetallePedido c;
        if(indice == -1) {
            c = new DetallePedido();
            c.setProducto(p);
            c.setCantidad(cantidad);
            lista.add(c);
        } else {
            c = lista.get(indice);
            c.AumentarCantidad(cantidad);
            
            if (c.getCantidad() > p.getStock()) {
                c.setCantidad(p.getStock());
            }
        }
        
        GuardarSession(request, lista);
        
        response.sendRedirect("CarritoControlador?accion=listar");
    }
    
    protected void ListarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<DetallePedido> lista = ObtenerSession(request);
        
        request.getSession().setAttribute("total", TotalPagar(lista));
        request.getRequestDispatcher("PagCarrito.jsp").forward(request, response);
    }
    
    public double TotalPagar(List<DetallePedido>lista) {
        double suma = 0;
        for (DetallePedido c : lista) {
            suma += c.Total();
        }
        return suma;
    }
    
    protected void EliminarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<DetallePedido> lista = ObtenerSession(request);
        
        int indice = request.getParameter("indice") == null ? -1 : Integer.parseInt(request.getParameter("indice"));
        
        if(indice >=0 && indice < lista.size()) {
            DetallePedido c = new DetallePedido();
            lista.remove(indice);
            GuardarSession(request, lista);
        } else {
            request.getSession().setAttribute("error", "No se ha podido quitar producto del carrito.");
        }
        
        response.sendRedirect("CarritoControlador?accion=listar");
    }
    
    
    public int BuscarPorId(List<DetallePedido>lista, int idProd) {
        for(int i=0; i < lista.size(); i++) {
            if(lista.get(i).getProducto().getIdProducto() == idProd)
                return i;
        }
        return -1;
    }
    
    public List<DetallePedido> ObtenerSession (HttpServletRequest request) {
        List<DetallePedido>lista;
        
        if(request.getSession().getAttribute("carrito") == null) {
            lista = new ArrayList<>();
        } else {
            lista = (List<DetallePedido>) request.getSession().getAttribute("carrito"); 
        }
        return lista;
    }
    
    public void GuardarSession(HttpServletRequest request, List<DetallePedido>lista) {
        request.getSession().setAttribute("carrito", lista);
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
