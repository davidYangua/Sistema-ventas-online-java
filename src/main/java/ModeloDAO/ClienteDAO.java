package ModeloDAO;

import Config.Conexion;
import Modelo.Cliente;
import Util.Hash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    
    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;
    
    public String guardar(Cliente obj, String salt) {
        String msj = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("{call grabarCliente(?,?,?,?,?,?,?,?)}");
            pt.setString(1, obj.getUsuario().getCorreo());
            pt.setString(2, obj.getUsuario().getPassword());
            pt.setString(3, obj.getDni());
            pt.setString(4, obj.getNombres());
            pt.setString(5, obj.getApellidos());
            pt.setString(6, obj.getTelefono());
            pt.setString(7, obj.getDireccion());
            pt.setString(8, salt);
            rs = pt.executeQuery();
            
            if(rs.next()) {
                msj = rs.getString(1);
            }
        } catch (Exception e) {
            msj = e.getMessage();
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
        return msj;
    }
    
    public int cantClientesRegistrados() {
        int res = 0;
        
        try {
            con = Conexion.getConexion();
            pt = con.prepareCall("select count(*) from Cliente");
            rs = pt.executeQuery();
            
            if(rs.next()) {
                res = rs.getInt(1);
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
        return res;
    }
}
