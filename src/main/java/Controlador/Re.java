package Controlador;

import Config.SunatAPI;
import ModeloDAO.ReporteDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Re extends HttpServlet {
    
    private String userHome = System.getProperty("user.home");
    private String downloadFolder = System.getProperty("os.name").toLowerCase().contains("win") ? "Downloads" : "Descargas";
    private String downloadPath = Paths.get(userHome, downloadFolder).toString();
    private ReporteDAO reporte = new ReporteDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "pedido":
                generarBoleta(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public void generarBoleta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SunatAPI sunatAPI = new SunatAPI();
        String nroPedido = request.getParameter("nro");
        try {
            // Generar el JSON de la factura
            String jsonBoleta = sunatAPI.generarJSONBoleta(reporte.obtenerDatosVenta(nroPedido));
            byte[] pdfBytes = sunatAPI.generarPDFBytes(jsonBoleta);

            // Enviar la boleta y obtener el ID
            String respuestaBoleta = sunatAPI.enviarBoleta(jsonBoleta);
            sunatAPI.generarPDFBytes(jsonBoleta);

            // Configurar la respuesta HTTP para enviar el PDF al navegador
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=boleta.pdf");
            response.setContentLength(pdfBytes.length);

            try (OutputStream out = response.getOutputStream()) {
                out.write(pdfBytes);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la boleta.");
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
