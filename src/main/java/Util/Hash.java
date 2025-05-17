package Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Hash {
    
    public static String sha256 (String password, String salt) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte [] digest = sha.digest((password+salt).getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            
            for(byte b : digest){
                hexString.append(String.format("%02x", b));
            }
            
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw  new RuntimeException(e);
        }
    }
    
    public static String generarSalt() {
        try {
            //Generar salt aleatorio
            SecureRandom rd = new SecureRandom();
            byte [] salt = new byte [16];
            rd.nextBytes(salt);
            
            return Base64.getEncoder().encodeToString(salt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
