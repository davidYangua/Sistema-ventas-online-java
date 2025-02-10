package Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class SunatAPI {

    private static final String LOGIN_URL = "https://facturacion.apisperu.com/api/v1/auth/login";
    private static final String SEND_INVOICE_URL = "https://facturacion.apisperu.com/api/v1/invoice/send";
    private static final String INVOICE_PDF_URL = "https://facturacion.apisperu.com/api/v1/invoice/pdf/";
    private static final String USERNAME = "davidYangua";
    private static final String PASSWORD = "gorilman";
    private static final String t = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1c2VybmFtZSI6ImRhdmlkWWFuZ3VhIiwiaWF0IjoxNzMyNjUwNDgzLCJleHAiOjE3MzI3MzY4ODN9.f8xzt9Qk-JUzMiOc3vhjCzJDdFg34HDtnWl9F3sS7d6lY7eYUazO6epjbMDJGi1MvJNHINK6ol6YYjRXyvUu0vBL9YeeUFjwCU_aDNNnLtdWANx6sFosGgOO1rl22VZE7fGTsjMhkhgMyAHGP-AJ6TS3S3voOwAZMm_cwF3hi1A2UN3krtKesu_W6rXGx2k5iwRX706cx93ApAnrTlQBegTTyM07xpcQtU1oIPTLd6YeXJGdKz4dBluLSKIpYrciQl-L3K2myiiQ9hqTHrVn_mhvQOrYSj3bAnyl2nTzUIKmEUcj4dNgfdiNeUHycdgM4aU42lD307UYP67GL9QLOIiaPnEH9Cbo-clydKT06HZ_nHMcHL80palRf-a51O4q7xN79UwSUN3w1n4XFPgRq3WnErevQJEW9J4NP0EVZAiu11b1iroF7ola_meCyiTVnNwOYBTQi2rL66APEg7VMQspeHhotLBYhle6KJW871qwy8NsnzFtdYk-sRt8UD8-gC2noC_MP1R784vA89TuAWP6IeU6W3LOoWy14xhXVUAnrWk0abdh5rYHeL81ZAEHLp90-joBnaXrYA7Rh_0XMc2gKKHBT4ZjY3twA5Ytplc4PHga8yxzIw-EFlOH7f-BOho-btYuga6zv0RMtlTJVCs9J1M6ypPSrwLYFJ3wk5M";

    private String token;
    private Instant tokenExpiration;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public SunatAPI() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String getToken() throws Exception {
        if (isTokenExpired()) {
            generateToken();
        }
        return token;
    }

    private boolean isTokenExpired() {
        return token == null || tokenExpiration == null || Instant.now().isAfter(tokenExpiration);
    }

    private String generateToken() throws Exception {
        try {
            // Crear el JSON con las credenciales
            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", USERNAME);
            credentials.put("password", PASSWORD);
            String requestBody = objectMapper.writeValueAsString(credentials);

            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LOGIN_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Enviar la solicitud
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir detalles de la respuesta para depuración
            //System.out.println("Estado HTTP: " + response.statusCode());
            //System.out.println("Cuerpo de la respuesta: " + response.body());
            // Validar el código de respuesta
            if (response.statusCode() == 200) {
                // Parsear la respuesta JSON
                Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);

                // Obtener el token
                this.token = (String) responseBody.get("token");

                // Establecer una duración fija de expiración (24 horas)
                this.tokenExpiration = Instant.now().plusSeconds(86400); // 24 horas en segundos

                //System.out.println("Token generado exitosamente: " + this.token);
                return this.token;
            } else {
                throw new RuntimeException("Error al generar el token: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Error al generar el token: " + e.getMessage());
            throw e;
        }
    }

    public String enviarFactura(String jsonBody) throws IOException, InterruptedException, Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SEND_INVOICE_URL))
                .header("Authorization", "Bearer " + getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Error al enviar factura: " + response.body());
        }
        System.out.println("Respuesta de enviarFactura: " + response.body());
        return response.body(); // Devuelve el JSON completo para extraer el ID si es necesario
    }

    public void generarPDF(String idFactura) throws IOException, InterruptedException, Exception {
        try {
            URL url = new URL("https://facturacion.apisperu.com/api/v1/invoice/pdf/" + idFactura);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + getToken());

            // Leer la respuesta
            int respuesta = con.getResponseCode();
            if (respuesta == 200) {
                // Guardar el PDF en un archivo
                String nombreArchivo = "C:/Users/Propietario/Downloads/boleta.pdf";
                try (FileOutputStream fos = new FileOutputStream(nombreArchivo)) {
                    fos.write(con.getInputStream().readAllBytes());
                }
                System.out.println("PDF generado y guardado en " + nombreArchivo);
            } else {
                // Leer el cuerpo de error
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String errorResponse = br.lines().collect(Collectors.joining());
                    throw new IOException("Error al generar PDF: " + errorResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String generarJsonDeFactura() {
        JsonObject factura = new JsonObject();
        factura.addProperty("ublVersion", "2.1");
        factura.addProperty("tipoOperacion", "0101");
        factura.addProperty("tipoDoc", "03");
        factura.addProperty("serie", "B001");
        factura.addProperty("correlativo", "1");
        factura.addProperty("fechaEmision", "2024-11-26T00:00:00-05:00");

        JsonObject formaPago = new JsonObject();
        formaPago.addProperty("moneda", "PEN");
        formaPago.addProperty("tipo", "Aplicativo movil");
        factura.add("formaPago", formaPago);

        factura.addProperty("tipoMoneda", "PEN");

        // Datos del cliente
        JsonObject client = new JsonObject();
        client.addProperty("tipoDoc", "1");
        client.addProperty("numDoc", "736457892");
        client.addProperty("rznSocial", "Cliente");

        JsonObject clientAddress = new JsonObject();
        clientAddress.addProperty("direccion", "Direccion cliente");
        clientAddress.addProperty("provincia", "Paita");
        clientAddress.addProperty("departamento", "Piura");
        clientAddress.addProperty("distrito", "Paita");
        clientAddress.addProperty("ubigueo", "150101");
        client.add("address", clientAddress);

        factura.add("client", client);

        // Datos de la empresa
        JsonObject company = new JsonObject();
        company.addProperty("ruc", "20000000046");
        company.addProperty("razonSocial", "REDPOINT");
        company.addProperty("nombreComercial", "REDPOINT");

        JsonObject companyAddress = new JsonObject();
        companyAddress.addProperty("direccion", "Av. Victor Raúl Halla de la Torre, Paita");
        companyAddress.addProperty("provincia", "Paita");
        companyAddress.addProperty("departamento", "Piura");
        companyAddress.addProperty("distrito", "Paita");
        companyAddress.addProperty("ubigueo", "150101");
        company.add("address", companyAddress);

        factura.add("company", company);

        factura.addProperty("mtoOperGravadas", 100);
        factura.addProperty("mtoIGV", 18);
        factura.addProperty("valorVenta", 100);
        factura.addProperty("totalImpuestos", 18);
        factura.addProperty("subTotal", 118);
        factura.addProperty("mtoImpVenta", 118);

        //Productos
        JsonObject details = new JsonObject();
        details.addProperty("codProducto", "P001");
        details.addProperty("descripcion", "PRODUCTO 1");
        details.addProperty("cantidad", 2);
        details.addProperty("mtoValorUnitario", 50);
        details.addProperty("mtoValorVenta", 100);
        details.addProperty("mtoBaseIgv", 100);
        details.addProperty("porcentajeIgv", 18);
        details.addProperty("igv", 18);
        details.addProperty("tipAfeIgv", 10);
        details.addProperty("totalImpuestos", 18);
        details.addProperty("mtoPrecioUnitario", 59);
        factura.add("details", details);

        JsonObject legends = new JsonObject();
        legends.addProperty("code", "1000");
        legends.addProperty("value", "SON CIENTO DIECIOCHO CON 00/100 SOLES");
        factura.add("legends", legends);

        return factura.toString();
    }

}
