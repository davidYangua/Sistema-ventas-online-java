package ModeloDAO;

import Config.Conexion;
import Modelo.Reporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteDAO {

    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;

    public List<Reporte> ListarReporte(String nombRol) {
        List<Reporte> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareCall("call Reporte_por_mes(?)");
            pt.setString(1, nombRol);
            rs = pt.executeQuery();
            while (rs.next()) {
                Reporte r = new Reporte();
                r.setNroMes(rs.getInt(1));
                r.setNombreMes(rs.getString(2));
                r.setCantidad(rs.getInt(3));
                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (pt != null) {
                    pt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    public Map<String, Object> obtenerDatosVenta(String nroPedido) {
        // Datos de la venta, cliente y productos
        Map<String, Object> datosVenta = new HashMap<>();
        List<Map<String, Object>> productos = new ArrayList<>();
        try {
            con = Conexion.getConexion();

            // Obtener datos de la venta
            pt = con.prepareStatement("SELECT CONCAT(c.nombre, CONCAT(' ', c.apellidos)) as 'Cliente', p.nroPed, p.montoTotal, c.dni, "
                    + "c.telefono, c.direccion, p.fecha FROM cliente c INNER JOIN pedido p "
                    + "on c.idCliente = p.idCliente WHERE p.nroPed = ?");

            pt.setString(1, nroPedido);
            rs = pt.executeQuery();

            if (rs.next()) {
                
                datosVenta.put("fecha", rs.getString(7));
                datosVenta.put("total", rs.getDouble(3));
                datosVenta.put("cliente", Map.of(
                        "tipoDoc", "1",
                        "numDoc", rs.getString(4),
                        "rznSocial", rs.getString(1),
                        "address", Map.of(
                                "direccion", rs.getString(6),
                                "provincia", "Paita",
                                "departamento", "Piura",
                                "distrito", "Paita",
                                "ubigueo", "150101"
                        )
                ));
            }

            // Obtener productos asociados a la venta
            pt = con.prepareStatement("SELECT pd.idProducto, CONCAT('P', LPAD(pd.idProducto, 7, '0')) AS codigoProducto, pd.nombre, d.cantidad,"
                    + "d.precio, p.montoTotal AS 'Total', p.nroPed, CONCAT(c.nombre, ' ', c.apellidos) AS 'Cliente', c.dni, c.telefono, "
                    + "c.direccion, p.fecha FROM detallepedido d INNER JOIN pedido p ON d.idPedidos = p.idPedidos"
                    + " INNER JOIN producto pd ON d.idProducto = pd.idProducto INNER JOIN cliente c ON c.idCliente = p.idCliente "
                    + "WHERE p.nroPed = ?");

            pt.setString(1, nroPedido);
            rs = pt.executeQuery();

            while (rs.next()) {
                productos.add(Map.of(
                        "codProducto", rs.getString(2),
                        "descripcion", rs.getString(3),
                        "cantidad", rs.getInt(4),
                        "mtoValorUnitario", rs.getDouble(5),
                        "mtoValorVenta", rs.getDouble(6)*0.82,
                        "mtoBaseIgv", rs.getDouble(6)*0.82,
                        "igv", 18,
                        "totalImpuestos", rs.getDouble(6)*0.18,
                        "tipAfeIgv", 10,
                        "mtoPrecioUnitario", rs.getDouble(5)*1.18  // Precio con IGV * 1.18
                ));
            }
            datosVenta.put("productos", productos);

        } catch (Exception e) {
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (pt != null) {
                    pt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return datosVenta;
    }

}
