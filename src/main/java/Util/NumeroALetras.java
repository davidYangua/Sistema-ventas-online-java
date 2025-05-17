package Util;

public class NumeroALetras {

    public static String convertir(double monto) {
        int parteEntera = (int) monto;
        int parteDecimal = (int) Math.round((monto - parteEntera) * 100);

        String letras = NumerosEnLetras.convertir(parteEntera).toUpperCase();

        return "SON " + letras + " CON " + String.format("%02d", parteDecimal) + "/100 SOLES";
    }
}
