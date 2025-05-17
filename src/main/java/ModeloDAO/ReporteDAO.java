package ModeloDAO;

import Config.Conexion;
import Modelo.Cliente;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Reporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        Map<String, Object> datos = new LinkedHashMap<>();
        List<Pedido> pedidos = new ArrayList<>();
        List<DetallePedido> detallePedidos = new ArrayList<>();
        Producto p = null;
        Pedido ped = null;
        DetallePedido det = null;
        
        try {
            con = Conexion.getConexion();

            // Obtener datos del cliente
            pt = con.prepareStatement("SELECT CONCAT(c.nombre, CONCAT(' ', c.apellidos)) as 'Cliente', p.nroPed, p.montoTotal, c.dni, "
                    + "c.telefono, c.direccion, p.fecha FROM cliente c INNER JOIN pedido p "
                    + "on c.idCliente = p.idCliente WHERE p.nroPed = ?");

            pt.setString(1, nroPedido);
            rs = pt.executeQuery();

            if (rs.next()) {
                Cliente c = new Cliente();
                c.setNombres(rs.getString(1));
                c.setDni(rs.getString(4));
                c.setDireccion(rs.getString(6));
                
                datos.put("cliente", c);
            }

            // Obtener productos asociados a la venta
            pt = con.prepareStatement("SELECT p.idPedidos,pd.idProducto, CONCAT('P', LPAD(pd.idProducto, 7, '0')) AS codigoProducto,"
                    + " pd.nombre, d.cantidad, d.precio, p.montoTotal AS 'Total', p.nroPed, CONCAT(c.nombre, ' ', c.apellidos) AS 'Cliente',"
                    + " c.dni, c.telefono, c.direccion, pg.idMetodoPago, p.fecha FROM detallepedido d INNER JOIN pedido p "
                    + "ON d.idPedidos = p.idPedidos INNER JOIN producto pd ON d.idProducto = pd.idProducto INNER JOIN cliente c "
                    + "ON c.idCliente = p.idCliente INNER JOIN pagopedido pg on pg.idPedidos = p.idPedidos WHERE p.nroPed = ?");

            pt.setString(1, nroPedido);
            rs = pt.executeQuery();

            while (rs.next()) {
                p = new Producto();
                ped = new Pedido();
                
                p.setCodProducto(rs.getString(3));
                p.setNombre(rs.getString(4));
                det = new DetallePedido();
                det.setProducto(p);
                det.setCantidad(rs.getInt(5));
                det.setPrecio(rs.getDouble(6));
                
                detallePedidos.add(det);
                
                ped.setIdPedido(rs.getInt(1));
                ped.setMetodoPago(rs.getInt(13));
                ped.setFecha(formatearFecha(rs.getString(14)));
                ped.setDetalles(detallePedidos);
                //pedidos.add(ped);
            }
            datos.put("pedidos", ped);

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
        return datos;
    }
    
    public String formatearFecha(String fecha) {
        //Parsear la fecha del formato de la base de datos
        DateTimeFormatter formatterBD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaLocal = LocalDateTime.parse(fecha, formatterBD);

        // Asignar zona horaria de Perú 
        ZonedDateTime fechaPeru = fechaLocal.atZone(ZoneId.of("America/Lima"));

        //Formato API
        DateTimeFormatter formatterAPI = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

        return fechaPeru.format(formatterAPI);
    }

}
