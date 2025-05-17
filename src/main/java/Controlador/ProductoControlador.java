package Controlador;

import Modelo.Categoria;
import Modelo.Producto;
import Modelo.RegistroProducto;
import Modelo.Usuario;
import ModeloDAO.CategoriaDAO;
import ModeloDAO.PedidoDAO;
import ModeloDAO.ProductoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductoControlador extends HttpServlet {
    
    private CategoriaDAO cat = new CategoriaDAO();
    private ProductoDAO prod = new ProductoDAO();
    private PedidoDAO p = new PedidoDAO();
    private String pagDelivery = "PagDelivery.jsp";
    private String pagFiltroDelivery = "vista/includes/PagDeliverySel.jsp";
    private String pagNuevoProducto = "vista/admin/PagNuevo.jsp";
    private String pagProductoGeneral = "vista/admin/PagProductoGeneral.jsp";
    private String adminRequest = "AdminControlador?accion=productos";
    private static Usuario objUsu;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
         objUsu = request.getSession().getAttribute("usuario") != null ? (Usuario) request.getSession().getAttribute("usuario") : new Usuario();
        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");
        
        switch (accion.toLowerCase()) {
            case "nuevo_producto":
                NuevoProducto(request, response);
                break;
            case "guardar":
                Guardar(request, response);
                break;
            case "editar":
                Editar(request, response);
                break;
            case "filtro_delivery":
                FiltroDelivery(request, response);
                break;
            case "categorias":
                listaCategorias(request, response);
                break;
            case "eliminar":
                Eliminar(request, response);
                break;
        }
    }
    
    protected void Guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //int idAdmin = 0;
        Producto p = new Producto();
        Categoria cat = new Categoria();
        cat.setIdCategoria(Integer.parseInt(request.getParameter("categoria").trim()));
        p.setIdProducto(Integer.parseInt(request.getParameter("id").trim()));
        p.setCategoria(cat);
        p.setNombre(request.getParameter("nombre").trim());
        p.setPrecio(Double.parseDouble(request.getParameter("precio").trim()));
        p.setStock(Integer.parseInt(request.getParameter("stock").trim()));
        p.setImagen(request.getParameter("imagen"));
        
        String msg = "";
        
        if(p.getIdProducto() == 0) {
            int idAdmin = Integer.parseInt(request.getParameter("idAdmin"));
            msg = prod.guardarProducto(p, idAdmin);
        } else {
            msg = prod.editar(p);
        }
        
        if(msg.equals("OK")){
            request.getSession().setAttribute("success", "Los datos se "+ (p.getIdProducto() == 0 ? "guardaron" : "editaron") + " de forma correcta.");
            response.sendRedirect(adminRequest);
        } else {
            request.setAttribute("producto", p);
            request.setAttribute("error", msg);
            response.sendRedirect(adminRequest);
            request.getRequestDispatcher(pagNuevoProducto).forward(request, response);
        }
        
        //request.getRequestDispatcher(pagProductoGeneral).forward(request, response);
            
        
    }
    
    protected void NuevoProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //objUsu = request.getSession().getAttribute("usu") != null ? (Usuario) request.getAttribute("usu") : new Usuario();
        List<Categoria>lista = cat.getCategorias();
        request.setAttribute("lista", lista);
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("producto", new Producto());
        request.setAttribute("cant_por_atender", p.CantidadPedidosPorAtender());
        request.getRequestDispatcher(pagNuevoProducto).forward(request, response);
    }
    
    protected void FiltroDelivery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String filtro = request.getParameter("filtro");
        int categ = Integer.parseInt(request.getParameter("categ"));
        int rango = Integer.parseInt(request.getParameter("rango"));
        request.setAttribute("listaProd", prod.ListarTodosDisponibles(filtro, categ, rango));
        request.getRequestDispatcher(pagFiltroDelivery).forward(request, response);
    }
    
    protected void listaCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        List<Categoria>lista = cat.getCategorias();
        request.setAttribute("lista", lista);
        request.getRequestDispatcher(pagDelivery).forward(request, response);
    }
    
    protected void Editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int idProd = Integer.parseInt(request.getParameter("idProd"));
        Producto pr = prod.getProducto(idProd);
        
        if(pr != null ){
            request.setAttribute("producto", pr);
            request.setAttribute("lista", cat.getCategorias());
            request.setAttribute("cant_por_atender", p.CantidadPedidosPorAtender());
            request.getRequestDispatcher(pagNuevoProducto).forward(request, response);
            return;
        }
        
        request.getSession().setAttribute("error", "No se pudo encontar el producto: "+pr.getNombre());
        response.sendRedirect(adminRequest);
    }
    
    protected void Eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int idProd = Integer.parseInt(request.getParameter("idProd"));
        String msg = "";
        
        msg = prod.eliminarProducto(idProd);
        
        if(msg.equals("OK")) {
            request.getSession().setAttribute("success", "Producto eliminado correctamente");
            response.sendRedirect(adminRequest);
        } else {
            request.setAttribute("error", msg);
            request.getRequestDispatcher(pagProductoGeneral).forward(request, response);
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
