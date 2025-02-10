package ModeloDAO;

import Config.Conexion;
import Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;

    public List<Categoria> getCategorias() {
        List<Categoria> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("select * from categorias");
            rs = pt.executeQuery();
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setIdCategoria(rs.getInt(1));
                cat.setNombreCat(rs.getString(2));
                lista.add(cat);
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
    
    public String nuevaCateogria(String categoria) {
        String msg = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("INSERT INTO categorias VALUES (null, ?)");
            pt.setString(1, categoria);
            msg =  pt.executeUpdate() > 0 ? "OK" : "No se pudo guardar la categoría";
            
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
    
    public String editarCateogria(int id, String categoria) {
        String msg = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("UPDATE categorias s set s.nombreCategoria = ? WHERE s.idCategorias = ?");
            pt.setString(1, categoria);
            pt.setInt(2, id);
            msg =  pt.executeUpdate() > 0 ? "OK" : "No se pudo editar la categoría";
            
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
    
    public String eliminarCateogria(int id) {
        String msg = "";
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("DELETE FROM categorias WHERE idCategorias = ?");
            pt.setInt(1, id);
            msg =  pt.executeUpdate() > 0 ? "OK" : "No se pudo eliminar la categoría";
            
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
