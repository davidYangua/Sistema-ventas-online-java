package Util;

public class NumerosEnLetras {

    private final static String[] UNIDADES = {
        "", "UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE"
    };

    private final static String[] DECENAS = {
        "", "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA",
        "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
    };

    private final static String[] CENTENAS = {
        "", "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS",
        "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"
    };

    public static String convertir(int numero) {
        if (numero == 0) return "CERO";
        if (numero == 100) return "CIEN";

        StringBuilder resultado = new StringBuilder();

        // Centenas
        if (numero >= 100) {
            int centenas = numero / 100;
            resultado.append(CENTENAS[centenas]).append(" ");
            numero %= 100;
        }

        // Decenas y unidades
        if (numero >= 30) {
            int decenas = numero / 10;
            int unidades = numero % 10;
            resultado.append(DECENAS[decenas]);
            if (unidades > 0) {
                resultado.append(" Y ").append(UNIDADES[unidades]);
            }
        } else if (numero >= 20) {
            int unidades = numero % 10;
            if (unidades == 0) {
                resultado.append("VEINTE");
            } else {
                resultado.append("VEINTI").append(UNIDADES[unidades].toLowerCase());
            }
        } else if (numero >= 10) {
            switch (numero) {
                case 10: resultado.append("DIEZ"); break;
                case 11: resultado.append("ONCE"); break;
                case 12: resultado.append("DOCE"); break;
                case 13: resultado.append("TRECE"); break;
                case 14: resultado.append("CATORCE"); break;
                case 15: resultado.append("QUINCE"); break;
                default: resultado.append("DIECI").append(UNIDADES[numero % 10].toLowerCase()); break;
            }
        } else if (numero > 0) {
            resultado.append(UNIDADES[numero]);
        }

        return resultado.toString().trim().toUpperCase();
    }
}
