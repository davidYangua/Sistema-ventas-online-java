package Controlador;

import Config.SunatAPI;
import ModeloDAO.ReporteDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReporteControlador extends HttpServlet {

    private String userHome = System.getProperty("user.home");
    private String downloadFolder = System.getProperty("os.name").toLowerCase().contains("win") ? "Downloads" : "Descargas";
    private String downloadPath = Paths.get(userHome, downloadFolder).toString();

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
        try {
            // Generar el JSON de la factura
            String jsonBody = sunatAPI.generarJsonDeFactura();

            // Enviar la factura y obtener el ID
            String respuestaFactura = sunatAPI.enviarFactura(jsonBody);
            JsonObject jsonResponse = JsonParser.parseString(respuestaFactura).getAsJsonObject();
            String idFactura = jsonResponse.get("id").getAsString();

            // Generar el PDF
            sunatAPI.generarPDF(idFactura);

            // Enviar el PDF al navegador
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=boleta.pdf");

            // Leer el PDF generado y enviarlo al cliente
            String rutaArchivo = "C:/Users/Propietario/Downloads/boleta.pdf";
            try (FileInputStream fis = new FileInputStream(rutaArchivo); OutputStream out = response.getOutputStream()) {
                fis.transferTo(out);
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la factura.");
            e.printStackTrace();
        }
    }

    public JsonObject construirJsonFactura(Map<String, Object> datosVenta) {
        JsonObject factura = new JsonObject();

        // Información básica
        factura.addProperty("ublVersion", "2.1");
        factura.addProperty("tipoOperacion", "0101");
        factura.addProperty("tipoDoc", "03");
        factura.addProperty("serie", "B001");
        factura.addProperty("correlativo", "1");
        factura.addProperty("fechaEmision", datosVenta.get("fecha").toString());

        // Forma de pago
        JsonObject formaPago = new JsonObject();
        formaPago.addProperty("moneda", "PEN");
        formaPago.addProperty("tipo", "Aplicativo movil");
        factura.add("formaPago", formaPago);

        // Moneda
        factura.addProperty("tipoMoneda", "PEN");

        // Cliente
        JsonObject cliente = new JsonObject();
        Map<String, Object> datosCliente = (Map<String, Object>) datosVenta.get("cliente");
        cliente.addProperty("tipoDoc", (String) datosCliente.get("tipoDoc"));
        cliente.addProperty("numDoc", (String) datosCliente.get("numDoc"));
        cliente.addProperty("rznSocial", (String) datosCliente.get("rznSocial"));

        JsonObject direccionCliente = new JsonObject();
        Map<String, Object> addressCliente = (Map<String, Object>) datosCliente.get("address");
        direccionCliente.addProperty("direccion", (String) addressCliente.get("direccion"));
        direccionCliente.addProperty("provincia", (String) addressCliente.get("provincia"));
        direccionCliente.addProperty("departamento", (String) addressCliente.get("departamento"));
        direccionCliente.addProperty("distrito", (String) addressCliente.get("distrito"));
        direccionCliente.addProperty("ubigueo", (String) addressCliente.get("ubigeo"));
        cliente.add("address", direccionCliente);

        factura.add("client", cliente);

        // Información de la empresa
        JsonObject company = new JsonObject();
        company.addProperty("ruc", "20000000046");
        company.addProperty("razonSocial", "REDPOINT");
        company.addProperty("nombreComercial", "REDPOINT");

        JsonObject direccionEmpresa = new JsonObject();
        direccionEmpresa.addProperty("direccion", "Av. Victor Raúl Halla de la Torre, Paita");
        direccionEmpresa.addProperty("provincia", "Paita");
        direccionEmpresa.addProperty("departamento", "Piura");
        direccionEmpresa.addProperty("distrito", "Paita");
        direccionEmpresa.addProperty("ubigueo", "150101");
        company.add("address", direccionEmpresa);

        factura.add("company", company);

        // Detalles de productos
        JsonArray detalles = new JsonArray();
        List<Map<String, Object>> productos = (List<Map<String, Object>>) datosVenta.get("productos");

        // Monto de la venta
        factura.addProperty("mtoOperGravadas", 100);
        factura.addProperty("mtoIGV", 18);
        factura.addProperty("valorVenta", 100);
        factura.addProperty("totalImpuestos", 18);
        factura.addProperty("subTotal", 118);
        factura.addProperty("mtoImpVenta", 118);

        for (Map<String, Object> producto : productos) {
            JsonObject detalle = new JsonObject();
            detalle.addProperty("codProducto", (String) producto.get("codProducto"));
            detalle.addProperty("unidad", "NIU");
            detalle.addProperty("descripcion", (String) producto.get("descripcion"));
            detalle.addProperty("cantidad", Integer.valueOf(producto.get("cantidad").toString()));
            detalle.addProperty("mtoValorUnitario", Double.valueOf(producto.get("mtoValorUnitario").toString()));
            detalle.addProperty("mtoValorVenta", Double.valueOf(producto.get("mtoValorVenta").toString()));
            detalle.addProperty("mtoBaseIgv", Double.valueOf(producto.get("mtoBaseIgv").toString()));
            detalle.addProperty("porcentajeIgv", 18);
            detalle.addProperty("igv", Double.valueOf(producto.get("igv").toString()));
            detalle.addProperty("tipAfeIgv", 10);
            detalle.addProperty("totalImpuestos", Double.valueOf(producto.get("totalImpuestos").toString()));
            detalle.addProperty("mtoPrecioUnitario", Double.valueOf(producto.get("mtoPrecioUnitario").toString()));
            detalles.add(detalle);
        }

        factura.add("details", detalles);

        // Leyenda
        JsonArray leyendas = new JsonArray();
        JsonObject leyenda = new JsonObject();
        leyenda.addProperty("code", "1000");
        leyenda.addProperty("value", "SON CIENTO DIECIOCHO CON 00/100 SOLES");
        leyendas.add(leyenda);
        factura.add("legends", leyendas);

        return factura;
    }

    public String enviarFactura(String token, String jsonBoleta) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("https://facturacion.apisperu.com/api/v1/invoice/send").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBoleta.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int statusCode = connection.getResponseCode();
        InputStream responseStream = statusCode == 200 ? connection.getInputStream() : connection.getErrorStream();

        return new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
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
