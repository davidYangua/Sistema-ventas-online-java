package ModeloDAO;

import Config.Conexion;
import Modelo.Categoria;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection con = null;
    private PreparedStatement pt = null;
    private ResultSet rs = null;

    public List<Producto> ListarTodosDisponibles(String filtro, int categoria, int rango) {
        List<Producto> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareCall("{call filtrar_productos (?,?,?)}");
            pt.setString(1, filtro);
            pt.setInt(2, categoria);
            pt.setInt(3, rango);
            rs = pt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                Categoria cat = new Categoria();
                p.setIdProducto(rs.getInt(1));
                cat.setIdCategoria(rs.getInt(2));
                cat.setNombreCat(rs.getString(7));
                p.setNombre(rs.getString(3));
                p.setPrecio(rs.getDouble(4));
                p.setStock(rs.getInt(5));
                p.setImagen(rs.getString(6));
                p.setCategoria(cat);
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
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
    
    public List<Producto> ListarTodos() {
        List<Producto> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            pt = con.prepareStatement("SELECT p.*, c.nombreCategoria as Categoria FROM producto p INNER JOIN categorias c on "
                    + "p.idCategorias = c.idCategorias");
            rs = pt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                Categoria cat = new Categoria();
                p.setIdProducto(rs.getInt(1));
                cat.setIdCategoria(rs.getInt(2));
                cat.setNombreCat(rs.getString(7));
                p.setNombre(rs.getString(3));
                p.setPrecio(rs.getDouble(4));
                p.setStock(rs.getInt(5));
                p.setImagen(rs.getString(6));
                p.setCategoria(cat);
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
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
    
    public String guardarProducto(Producto prod, int idAdmin){
        con = Conexion.getConexion();
        String msg = "";
        try {
            pt = con.prepareCall("{call agregar_Producto(?,?,?,?,?,?)}");
            pt.setInt(1, prod.getCategoria().getIdCategoria());
            pt.setString(2, prod.getNombre());
            pt.setDouble(3, prod.getPrecio());
            pt.setInt(4, prod.getStock());
            pt.setString(5, prod.getImagen());
            pt.setInt(6, idAdmin);
            
            msg = pt.executeUpdate() > 0 ? "OK" : "No se pudo guardar datos del producto";
            
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
    
    public Producto getProducto(int id) {
        Producto p = null;
        con = Conexion.getConexion();
        try {
            pt = con.prepareStatement("SELECT p.*, c.nombreCategoria FROM producto p INNER JOIN categorias c on "
                    + "p.idCategorias = c.idCategorias WHERE idProducto = ?");
            pt.setInt(1, id);
            rs = pt.executeQuery();
            if(rs.next()) {
                p = new Producto();
                Categoria cat = new Categoria();
                p.setIdProducto(rs.getInt(1));
                cat.setIdCategoria(rs.getInt(2));
                cat.setNombreCat(rs.getString(7));
                p.setCategoria(cat);
                p.setNombre(rs.getString(3));
                p.setPrecio(rs.getDouble(4));
                p.setStock(rs.getInt(5));
                p.setImagen(rs.getString(6));
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
        return p;
    } 
    
    public String editar(Producto p) {
        con = Conexion.getConexion();
        String msg = "";
        try {
            pt = con.prepareStatement("UPDATE producto p SET p.idCategorias = ?, p.nombre = ?, p.precio = ?,"
                    + " p.stock = ?, p.imagen = ? WHERE p.idProducto = ?");
            pt.setInt(1, p.getCategoria().getIdCategoria());
            pt.setString(2, p.getNombre());
            pt.setDouble(3, p.getPrecio());
            pt.setInt(4, p.getStock());
            pt.setString(5, p.getImagen());
            pt.setInt(6, p.getIdProducto());
            msg = pt.executeUpdate() > 0 ? "OK" : "No se puedo editar datos del producto";
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
    
    public String eliminarProducto(int id) {
        con = Conexion.getConexion();
        String msg = "";
        try {
            pt = con.prepareStatement("DELETE FROM producto WHERE idProducto = ?");
            pt.setInt(1, id);
            
            msg = pt.executeUpdate() > 0 ? "OK" : "No se pudo eliminar el producto";
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
