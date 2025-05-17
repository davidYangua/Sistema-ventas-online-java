package ModeloDAO;

import Config.Conexion;
import Modelo.Comentarios;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;

    public List<Comentarios> getComentarios() {
        List<Comentarios> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT u.idUsuario, u.correo, c.idComentarios, CONCAT('C', LPAD(SUBSTRING_INDEX(l.idCliente, 'C', -1) , 7 , '0')) AS Nro,"
                    + " CONCAT(l.nombre, ' ', l.apellidos) AS nombre, l.telefono, c.fecha, c.comentario,p.nroPed FROM comentarios c "
                    + "INNER JOIN cliente l ON c.idCliente = l.idCliente INNER JOIN usuario u ON l.idUsuario = u.idUsuario "
                    + "INNER JOIN pedido p ON p.idCliente = l.idCliente WHERE c.comentario IS NOT NULL AND c.fecha IS NOT NULL "
                    + "GROUP BY u.idUsuario, c.idComentarios, c.idCliente, l.nombre, l.apellidos, l.telefono, c.fecha, c.comentario;");
            rs = pt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                Comentarios c = new Comentarios();
                u.setIdUsu(rs.getInt(1));
                u.setCorreo(rs.getString(2));
                c.setIdComentario(rs.getInt(3));
                c.setCodCliente(rs.getString(4));
                u.setUsername(rs.getString(5));
                c.setFecha(rs.getString(7));
                c.setTexto(rs.getString(8));
                c.setNroPed(rs.getString(9));
                c.setUsuario(u);
                if (!c.getTexto().equals("")) {
                    lista.add(c);
                }
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
}
