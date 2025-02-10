package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    public static Connection getConexion() {
        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3307/tienda";
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión Exitosa");
        } catch (ClassNotFoundException e) {
            System.out.println("No hay driver: "+e.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
