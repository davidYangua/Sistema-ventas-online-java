package Util;

import Modelo.Cliente;
import Modelo.Usuario;

public class AuthFilter {

    public static boolean esAdmin(Usuario obj) {
        if (obj != null) {
            if (Constantes.ROL_ADMINISTRADOR.equalsIgnoreCase(obj.getRol().getNombreRol())) {
                return true;
            }
        }
        return false;
    }

    public static boolean esCliente(Usuario obj) {
        if (obj != null) {
            if (Constantes.ROL_CLIENTE.equals(obj.getRol().getNombreRol())) {
                return true;
            }
        }
        return false;
    }
}
