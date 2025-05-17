package ModeloDAO;

import Config.Conexion;
import Modelo.Cliente;
import Modelo.Delivery;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Modelo.Producto;
import Util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    
    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;
    
    public String GuardarPedido(Pedido p, int metPago, String direccion, String comentario) {
        String msg = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareCall("{call Generar_Pedido (?,?,?,?,?,?)}");
            pt.setInt(1, p.getCliente().getIdCliente());
            pt.setDouble(2, p.getMontoTotal());
            pt.setString(3, Constantes.ESTADO_PROCESO);
            pt.setInt(4, metPago);
            pt.setString(5, direccion);
            pt.setString(6, comentario);
            rs = pt.executeQuery();
            
            if (rs.next()) {
                int idPed = rs.getInt(1);
                String codPed = rs.getString(2);
                
                for(DetallePedido d : p.getDetalles()) {
                    pt = con.prepareCall("{call Grabar_DetPedido (?,?,?,?,?,?)}");
                    pt.setInt(1,idPed);
                    pt.setInt(2, d.getProducto().getIdProducto());
                    pt.setInt(3, d.getCantidad());
                    pt.setDouble(4, d.getProducto().getPrecio());
                    pt.setInt(5, Constantes.ESTADO_DET_NO_ATENDIDO);
                    pt.setString(6, codPed);
                    pt.executeUpdate();
                }
                msg = "OK";
            } else {
                msg = "No se ha podido procesar el pedido";
            }
        } catch (Exception e) {
            msg = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (pt != null) {
                    pt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return msg;
    }
    
    public List<Pedido> ListarPorCliente(int idCliente) {
        List<Pedido>lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT  p.*, c.*, d.* FROM pedido p INNER JOIN cliente c on p.idCliente = c.idCliente"
                    + " INNER JOIN delivery d on p.idDelivery = d.idDelivery WHERE c.idCliente = ?");
            pt.setInt(1, idCliente);
            rs = pt.executeQuery();
            while(rs.next()){
                Cliente c = new Cliente();
                Pedido p = new Pedido();
                Delivery d = new Delivery();
                p.setIdPedido(rs.getInt(1));
                p.setNroPed(rs.getString(2));
                c.setIdCliente(rs.getInt(3));
                d.setIdDelivery(rs.getInt(4));
                p.setFecha(rs.getString(5));
                p.setMontoTotal(rs.getDouble(6));
                p.setEstado(rs.getString(7));
                c.setNombres(rs.getString(11));
                c.setApellidos(rs.getString(12));
                c.setDni(rs.getString(13));
                c.setTelefono(rs.getString(14));
                c.setDireccion(rs.getString(15));
                d.setIdDelivery(rs.getInt(16));
                d.setHora(rs.getTime(18));
                d.setDireccion(rs.getString(19));
                p.setDelivery(d);
                p.setCliente(c);
                lista.add(p);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
    
    public Pedido BuscarPorId(String nro){
        Pedido p = null;
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT * FROM pedido p INNER JOIN cliente c ON p.idCliente = c.idCliente WHERE p.nroPed = ?");
            pt.setString(1, nro);
            rs = pt.executeQuery();
            if(rs.next()){
                Cliente c = new Cliente();
                p = new Pedido();
                Delivery d = new Delivery();
                p.setIdPedido(rs.getInt(1));
                p.setNroPed(rs.getString(2));
                c.setIdCliente(rs.getInt(3));
                d.setIdDelivery(rs.getInt(4));
                p.setFecha(rs.getString(5));
                p.setMontoTotal(rs.getDouble(6));
                p.setEstado(rs.getString(7));
                c.setNombres(rs.getString(11));
                c.setApellidos(rs.getString(12));
                c.setDni(rs.getString(13));
                c.setTelefono(rs.getString(14));
                c.setDireccion(rs.getString(15));
                p.setCliente(c);
                p.setDelivery(d);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return p;
    }
    
    public List<DetallePedido> ListarDetallePorPedido(String nroPed) {
        List<DetallePedido> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT d.*, p.nombre, p.imagen FROM detallepedido d INNER JOIN producto p "
                    + "on d.idProducto = p.idProducto WHERE d.nroPed = ?");
            pt.setString(1, nroPed);
            rs = pt.executeQuery();
            while(rs.next()){
                Producto p = new Producto();
                DetallePedido d = new DetallePedido();
                d.setIdDetallePed(rs.getInt(1));
                d.setCantidad(rs.getInt(5));
                d.setPrecio(rs.getDouble(6));
                d.setAtendido(rs.getInt(7));
                p.setNombre(rs.getString(8));
                p.setImagen(rs.getString(9));
                d.setProducto(p);
                lista.add(d);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
    
    public List<Pedido> ListaPedidosGeneral () {
        List<Pedido> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT p.*,c.*, fn_cantDetPedido(p.nroPed) as 'cantidad', d.horaEntrega, d.direccion as 'cantidad' "
                    + "FROM pedido p INNER JOIN cliente c on c.idCliente = p.idCliente INNER JOIN delivery d on d.idDelivery = p.idDelivery");
            rs = pt.executeQuery();
            while(rs.next()) {
                Cliente c = new Cliente();
                Pedido p = new Pedido();
                Delivery d = new Delivery();
                p.setIdPedido(rs.getInt(1));
                p.setNroPed(rs.getString(2));
                c.setIdCliente(rs.getInt(3));
                d.setIdDelivery(rs.getInt(4));
                p.setFecha(rs.getString(5));
                p.setMontoTotal(rs.getDouble(6));
                p.setEstado(rs.getString(7));
                c.setNombres(rs.getString(11));
                c.setApellidos(rs.getString(12));
                c.setDni(rs.getString(13));
                c.setTelefono(rs.getString(14));
                c.setDireccion(rs.getString(15));
                p.setCantDetalles(rs.getInt(16));
                d.setHora(rs.getTime(17));
                d.setDireccion(rs.getString(18));
                p.setDelivery(d);
                p.setCliente(c);
                lista.add(p);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
    
    public String Atender(String nroPed) {
        String msg = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("UPDATE detallepedido d SET d.atendido = 1 WHERE d.nroPed = ?");
            pt.setString(1, nroPed);
            msg = pt.executeUpdate() > 0 ? "OK" : "Lo sentimos! No se pudo atender pedido";
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return msg;
    }
    
    public int CantPedidoPorAntend(String nroPed){
        int cant = 0;
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("select fn_cantDetPedido(?)");
            pt.setString(1, nroPed);
            rs = pt.executeQuery();
            if(rs.next()){
                cant = rs.getInt(1);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cant;
    }
    
    public String ActualizarEstadoPedido (String nroPed, String estado) {
        String msg = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("UPDATE pedido p SET p.estado = ? WHERE p.nroPed = ?");
            pt.setString(1, estado);
            pt.setString(2, nroPed);
            
            msg = pt.executeUpdate() > 0 ? "OK" : "Lo sentimos! No se pudo actualizar estado del pedido";
            
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return msg;
    }
    
    public int CantidadPedidosRealizados() {
        int cant = 0;
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT COUNT(*) FROM pedido");
            rs = pt.executeQuery();
            if(rs.next()){
                cant = rs.getInt(1);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cant;
    }
    
    public int CantidadPedidosPorAtender() {
        int cant = 0;
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT COUNT(*) FROM pedido p WHERE p.estado = 'En proceso'");
            rs = pt.executeQuery();
            if(rs.next()){
                cant = rs.getInt(1);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cant;
    }
    
    public int CantidadPedidosAtendidos() {
        int cant = 0;
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT COUNT(*) FROM pedido p WHERE p.estado = 'Atendido'");
            rs = pt.executeQuery();
            if(rs.next()){
                cant = rs.getInt(1);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cant;
    }
}
