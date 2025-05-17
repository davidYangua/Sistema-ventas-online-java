package ModeloDAO;

import Config.Conexion;
import Modelo.Cliente;
import Modelo.Rol;
import Modelo.Usuario;
import Util.Hash;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UsuarioDAO {

    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;

    public Usuario autentificar(String correo, String password) {
        Usuario u = null;
        con = Conexion.getConexion();
        String salt = "";
        try {
            pt = con.prepareStatement("select salt from usuario where correo = ?");
            pt.setString(1, correo);
            rs = pt.executeQuery();
            if (rs.next()) {
                salt = rs.getString(1);
            }
            pt = con.prepareCall("{call loginUsuario(?,?)}");
            pt.setString(1, correo);
            pt.setString(2, Hash.sha256(password, salt));
            rs = pt.executeQuery();
            if (rs.next()) {
                Rol r = new Rol();
                u = new Usuario();
                r.setIdRol(rs.getInt(7));
                r.setNombreRol(rs.getString(9));
                u.setIdUsu(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setCorreo(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setEstado(rs.getInt(5));
                u.setFecha(rs.getTimestamp(6).toString());
                u.setId(rs.getInt(8)); //idCliente o idAdministrador
                u.setRol(r);
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
        return u;
    }

    public List<Cliente> getUsuarios() {
        List<Cliente> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT c.idCliente, CONCAT('U', LPAD(SUBSTRING_INDEX(u.idUsuario, 'U', -1) , 7 , '0')) AS 'Nro', "
                    + "CONCAT(c.nombre, CONCAT(' ',c.apellidos)) as 'username', u.correo, c.direccion, c.telefono,u.fecha,u.estado "
                    + "FROM usuario u INNER JOIN cliente c ON u.idUsuario = c.idUsuario");
            rs = pt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt(1));
                u.setCodUsuario(rs.getString(2));
                u.setUsername(rs.getString(3));
                u.setCorreo(rs.getString(4));
                c.setDireccion(rs.getString(5));
                c.setTelefono(rs.getString(6));
                u.setFecha(rs.getString(7));
                u.setEstado(rs.getInt(8));
                c.setUsuario(u);
                lista.add(c);
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

    public String cambaiarEstado(int id, int estado) {
        String msg = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareCall("{call cambiar_estado(?,?)}");
            pt.setInt(1, id);
            pt.setInt(2, estado);
            msg = pt.executeUpdate() > 0 ? "OK" : "Error al cambiar el estado";
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
        return msg;
    }
}
