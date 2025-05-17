package Config;

import Modelo.Categoria;
import Modelo.Cliente;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Usuario;
import ModeloDAO.CategoriaDAO;
import ModeloDAO.PedidoDAO;
import ModeloDAO.ProductoDAO;
import ModeloDAO.UsuarioDAO;
import Config.*;
import ModeloDAO.ReporteDAO;
import Util.Hash;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {

        Conexion.getConexion();
        /*UsuarioDAO u = new UsuarioDAO();
        Usuario us = u.autentificar("admin@gmail.com", "admin");
        System.out.println(us);
        System.out.println(us.getFecha());
        System.out.println(us.getUsername());
        System.out.println(us.getRol().getNombreRol());
        //System.out.println(Hash.generarSalt()); */

 /*String salt = "";
        salt = Hash.generarSalt();
        System.out.println("Salt: "+salt);
        System.out.println("password: "+Hash.sha256("admin", salt));*/
//        String salt = Hash.generarSalt();
//        System.out.println("Password: "+Hash.sha256("Hola", salt));
//        System.out.println("Salt: "+salt);
        //List<Producto>lista  = new ProductoDAO().ListarTodosDisponibles();
//        for(Producto i: lista){
//            System.out.println(i.getIdProducto()+") "+i.getNombre());
//        }
        SunatAPI client = new SunatAPI();
        //String token = client.getToken();
        //System.out.println("Token generado: " + token);
        //System.out.println(client.generarJsonDeFactura());

//            ReporteDAO r = new ReporteDAO();
////          Map<String, Object> d = r.obtenerDatosVenta("P0000001");
////          for(Map.Entry<String, Object> i : d.entrySet()){
////              System.out.println(i.getKey()+" -> "+i.getValue());
////          }
//            Map<String, Object> datosVenta = r.obtenerDatosVenta("P0000001");
//            List<Map<String, Object>> productos = (List<Map<String, Object>>)datosVenta.get("productos");
//            SunatAPI s = new SunatAPI();
//            
//            for(Map<String, Object> p : productos) {
//                
//            } 


        Map<String,Object> map = new LinkedHashMap<>();
        SunatAPI api = new SunatAPI();
        ReporteDAO r = new ReporteDAO();
        
        map = r.obtenerDatosVenta("P0000001");
        System.out.println(api.generarJSONBoleta(map));
    }

}
