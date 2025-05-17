package Config;

import Modelo.Cliente;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Util.NumeroALetras;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SunatAPI {

    private static final String LOGIN_URL = "https://facturacion.apisperu.com/api/v1/auth/login";
    private static final String SEND_INVOICE_URL = "https://facturacion.apisperu.com/api/v1/invoice/send";
    private static final String INVOICE_PDF_URL = "https://facturacion.apisperu.com/api/v1/invoice/pdf";
    private static final String USERNAME = "davidYangua";
    private static final String PASSWORD = "gorilman";

    private String token;
    private Instant tokenExpiration;

    private final HttpClient client;
    private Gson gson;

    public SunatAPI() {
        this.client = HttpClient.newHttpClient();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
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
            Map<String, String> credenciales = new LinkedHashMap<>();
            credenciales.put("username", USERNAME);
            credenciales.put("password", PASSWORD);

            String jsonCredencials = gson.toJson(credenciales); //convertimos los datos a json

            HttpRequest request = HttpRequest.newBuilder() //Solicitud
                    .uri(URI.create(LOGIN_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonCredencials))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Map<String, String> rsp = gson.fromJson(response.body(), new TypeToken<Map<String, String>>() {
            }.getType());
            this.token = rsp.get("token");
            this.tokenExpiration = Instant.now().plusSeconds(86400);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return token;
    }

    public String generarJSONBoleta(Map<String, Object> datos) {
        String jsonBoleta = "";
        double subTotal = 0;
        double total = 0;

        Pedido pedido = (Pedido) datos.get("pedidos");
        Cliente cliente = (Cliente) datos.get("cliente");
        List<DetallePedido> listaPedidos = pedido.getDetalles();

        Map<String, Object> boleta = new LinkedHashMap<>();
        boleta.put("ublVersion", "2.1");
        boleta.put("tipoOperacion", "0101");
        boleta.put("tipoDoc", "03");
        boleta.put("serie", "B000001"); //cantidad variable
        boleta.put("correlativo", String.valueOf(pedido.getIdPedido()));
        boleta.put("fechaEmision", pedido.getFecha());

        Map<String, String> formaPago = new LinkedHashMap<>();
        formaPago.put("moneda", "PEN");
        formaPago.put("tipo", pedido.getMetodoPago() == 1 ? " Pago mediante Yape" : " Pago mediante Plin");
        boleta.put("formaPago", formaPago);

        boleta.put("tipoMoneda", "PEN");

        Map<String, Object> client = new LinkedHashMap<>();
        client.put("tipoDoc", "1");
        client.put("numDoc", cliente.getDni());
        client.put("rznSocial", cliente.getNombres());

        Map<String, Object> address = new LinkedHashMap<>();
        address.put("direccion", cliente.getDireccion());
        address.put("provincia", "Paita");
        address.put("departamento", "Piura");
        address.put("distrito", "Paita");
        address.put("ubigueo", "150101");
        client.put("address", address);

        boleta.put("client", client);

        Map<String, Object> company = new LinkedHashMap<>();
        company.put("ruc", "20000000046");
        company.put("razonSocial", "REDPOINT");
        company.put("nombreComercial", "REDPOINT");

        Map<String, Object> empresaAddress = new LinkedHashMap<>();
        empresaAddress.put("direccion", "Av. Victor Raúl Halla de la Torre, Paita");
        empresaAddress.put("provincia", "Paita");
        empresaAddress.put("departamento", "Piura");
        empresaAddress.put("distrito", "Paita");
        empresaAddress.put("ubigueo", "150101");
        company.put("address", empresaAddress);

        boleta.put("company", company);

        subTotal = listaPedidos.stream().map(DetallePedido::getSubTotalProducto).reduce(0.0, (a, b) -> a + b);
        total = listaPedidos.stream().map(DetallePedido::getTotal).reduce(0.0, (a, b) -> a + b);

        boleta.put("mtoOperGravadas", subTotal);
        boleta.put("mtoIGV", (double) (total * 0.18));
        boleta.put("valorVenta", 100);
        boleta.put("totalImpuestos", 18);
        boleta.put("subTotal", 118);
        boleta.put("mtoImpVenta", total);

        List<Map<String, Object>> details = new ArrayList<>();

        for (DetallePedido i : listaPedidos) {
            Map<String, Object> detail = new LinkedHashMap<>();
            detail.put("codProducto", i.getProducto().getCodProducto());
            detail.put("unidad", "NIU");
            detail.put("descripcion", i.getProducto().getNombre());
            detail.put("cantidad", i.getCantidad());
            detail.put("mtoValorUnitario", i.getSubTotalPrecioUnitario()); //aplicar método de descuento de 18% al precio del producto
            detail.put("mtoValorVenta", i.getSubTotalProducto());  //aplicar método de descuento de 18% al catidad*producto
            detail.put("mtoBaseIgv", i.getTotal());    //aplicar método de descuento de 18% esto es por pedido total
            detail.put("porcentajeIgv", 18);
            detail.put("igv", 18);
            detail.put("tipAfeIgv", 10);
            detail.put("totalImpuestos", (double) (i.getTotal() * 0.18));
            detail.put("mtoPrecioUnitario", i.getPrecio());
            details.add(detail);
        }
        boleta.put("details", details);

        List<Map<String, Object>> legends = new ArrayList<>();
        Map<String, Object> legend = new LinkedHashMap<>();
        legend.put("code", 1000);
        legend.put("value", NumeroALetras.convertir(total));
        legends.add(legend);
        boleta.put("legends", legends);

        jsonBoleta = gson.toJson(boleta);

        return jsonBoleta;
    }

    public String enviarBoleta(String jsonBoleta) throws IOException, InterruptedException, Exception {
        String str = "";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SEND_INVOICE_URL))
                    .header("Authorization", "Bearer " + getToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBoleta))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new Exception("Error al enviar la factura");
            } else {
                str = response.body();
                System.out.println(response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return str;
    }

    //                byte[] buffer = new byte[4096];
//                int byteRead;
//                while((byteRead = input.read(buffer)) != -1) {
//                    output.write(buffer,0,byteRead);
//   
    public byte[] generarPDFBytes(String jsonBoleta) throws Exception {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(INVOICE_PDF_URL))
                    .header("Authorization", "Bearer " + getToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBoleta))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() != 200 || response.body() == null || response.body().length == 0) {
                throw new Exception("Error al generar el PDF de la boleta. Código: " + response.statusCode());
            }

            return response.body();

        } catch (Exception e) {
            e.printStackTrace(); // Para diagnóstico en consola
            throw new Exception("Error al generar el PDF de la boleta: " + e.getMessage());
        }
    }

}
