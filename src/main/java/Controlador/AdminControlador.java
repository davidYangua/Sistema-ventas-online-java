package Controlador;

import Modelo.Categoria;
import Modelo.Pedido;
import Modelo.Reporte;
import Modelo.Usuario;
import ModeloDAO.CategoriaDAO;
import ModeloDAO.ClienteDAO;
import ModeloDAO.ComentarioDAO;
import ModeloDAO.PedidoDAO;
import ModeloDAO.ProductoDAO;
import ModeloDAO.ReporteDAO;
import ModeloDAO.UsuarioDAO;
import Util.Constantes;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminControlador extends HttpServlet {

    private String pagProductos = "vista/admin/PagProductoGeneral.jsp";
    private String pagPedidos = "vista/admin/PagPedidosGeneral.jsp";
    private String pagInicio = "vista/admin/PanelAdmin.jsp";
    private String pagComentarios = "vista/admin/vistaComentarios.jsp";
    private String pagUsuarios = "vista/admin/PagGestionUsuario.jsp";
    private ProductoDAO prod = new ProductoDAO();
    private PedidoDAO p = new PedidoDAO();
    private ReporteDAO report = new ReporteDAO();
    private ClienteDAO cl = new ClienteDAO();
    private static Usuario usu;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");;

        usu = request.getSession().getAttribute("usuario") != null ? (Usuario) request.getSession().getAttribute("usuario") : new Usuario();

        String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

        switch (accion.toLowerCase()) {
            case "inicio":
                Inicio(request, response);
                break;
            case "productos":
                ProductosGeneral(request, response);
                break;
            case "pedidos":
                Pedidos(request, response);
                break;
            case "atender_pedido":
                Atender(request, response);
                break;
            case "reporte":
                ListadoReporte(request, response);
                break;
            case "pedidos_en_espera":
                Pedidos_en_Espera(request, response);
                break;
            case "comentarios":
                Gestion_de_Comentarios(request, response);
                break;
            case "agregar_categoria":
                Agregar_Categoria(request, response);
                break;
            case "editar_categoria":
                Editar_Categoria(request, response);
                break;
            case "eliminar_categoria":
                Eliminar_Categoria(request, response);
                break;
            case "gestion_usuarios":
                Gestion_Usuarios(request, response);
                break;
            case "cambiar_estado":
                Cambiar_Estado_Usuario(request, response);
                break;
        }
    }

    protected void ProductosGeneral(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        CategoriaDAO c = new CategoriaDAO();
        List<Categoria> lista = c.getCategorias();

        request.setAttribute("lista", prod.ListarTodos());
        request.setAttribute("cant_por_atender", p.CantidadPedidosPorAtender());
        request.setAttribute("categorias", lista);
        //request.getSession().setAttribute("usuario", usu);
        request.getRequestDispatcher(pagProductos).forward(request, response);
    }

    protected void Pedidos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("cant_por_atender", p.CantidadPedidosPorAtender());
        request.setAttribute("lista", p.ListaPedidosGeneral());
        request.getRequestDispatcher(pagPedidos).forward(request, response);
    }

    protected void Atender(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String nroPed = request.getParameter("nro");
        String msg = p.Atender(nroPed);

        if (msg.equals("OK")) {
            request.getSession().setAttribute("success", "Pedido atendido correctamente");

            int cant = p.CantPedidoPorAntend(nroPed);

            if (cant == 0) {
                p.ActualizarEstadoPedido(nroPed, Constantes.ESTADO_ATENDIDO);
            }

        } else {
            request.getSession().setAttribute("error", msg);
        }
        response.sendRedirect("AdminControlador?accion=pedidos");

    }

    protected void ListadoReporte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String nomRol = request.getParameter("rol");
        List<Reporte> lista = new ArrayList<>();

        lista = report.ListarReporte(nomRol);

        out.print(gson.toJson(lista));
    }

    protected void Inicio(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("cant_clientes", cl.cantClientesRegistrados());
        request.setAttribute("cant_por_atender", p.CantidadPedidosPorAtender());
        request.setAttribute("cant_pedidos", p.CantidadPedidosRealizados());
        request.setAttribute("pedidos_atendidos", p.CantidadPedidosAtendidos());
        request.getRequestDispatcher(pagInicio).forward(request, response);
    }

    protected void Pedidos_en_Espera(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter o = response.getWriter();
        Gson gson = new Gson();

        List<Pedido> lista = p.ListaPedidosGeneral();
        int pedidosEnEspera = 0;
        for (Pedido pedido : lista) {
            if ("En proceso".equals(pedido.getEstado())) {
                pedidosEnEspera++;
            }
        }

        // Enviar el JSON con el conteo de nuevos pedidos, asegurando que siempre haya una respuesta válida
        o.print(gson.toJson(pedidosEnEspera));
        //o.flush();
        //o.close();
    }

    protected void Gestion_de_Comentarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        ComentarioDAO c = new ComentarioDAO();
        request.setAttribute("lista", c.getComentarios());
        request.setAttribute("cant_por_atender", p.CantidadPedidosPorAtender());
        request.getRequestDispatcher(pagComentarios).forward(request, response);
    }

    protected void Agregar_Categoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        CategoriaDAO c = new CategoriaDAO();
        String nuevaCat = request.getParameter("nuevaCat");

        String msg = c.nuevaCateogria(nuevaCat);

        if (msg.equals("OK")) {
            request.getSession().setAttribute("success", "Se añadió correctamente");
        } else {
            request.setAttribute("error", msg);
        }
        response.sendRedirect("AdminControlador?accion=productos");
    }

    protected void Editar_Categoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        CategoriaDAO c = new CategoriaDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        String editCat = request.getParameter("editCat");

        String msg = c.editarCateogria(id, editCat);

        if (msg.equals("OK")) {
            request.getSession().setAttribute("success", "Se guardaron los cambios correctamente");
        } else {
            request.setAttribute("error", msg);
        }
        response.sendRedirect("AdminControlador?accion=productos");
    }
    
    protected void Eliminar_Categoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        CategoriaDAO c = new CategoriaDAO();
        int id = Integer.parseInt(request.getParameter("id"));

        String msg = c.eliminarCateogria(id);

        if (msg.equals("OK")) {
            request.getSession().setAttribute("success", "Se eliminó correctamente");
        } else {
            request.setAttribute("error", msg);
        }
        response.sendRedirect("AdminControlador?accion=productos");
    }
    
    protected void Gestion_Usuarios (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        UsuarioDAO u = new UsuarioDAO();
        
        request.setAttribute("lista", u.getUsuarios());
        request.setAttribute("cant_por_atender", p.CantidadPedidosPorAtender());
        request.getRequestDispatcher(pagUsuarios).forward(request, response);
    }
    
    protected void Cambiar_Estado_Usuario (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        UsuarioDAO u = new UsuarioDAO();
        
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int estado = Integer.parseInt(request.getParameter("nuevoEstado"));
        String msg = u.cambaiarEstado(idCliente, estado);
        
        if (msg.equals("OK")) {
            request.getSession().setAttribute("success", "Estado modificado correctamente");

        } else {
            request.getSession().setAttribute("error", msg);
        }
        
        response.sendRedirect("AdminControlador?accion=gestion_usuarios");
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
